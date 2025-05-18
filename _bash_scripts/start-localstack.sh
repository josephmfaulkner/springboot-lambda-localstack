#!/bin/bash
set -x #echo on

# Define the path to your Docker Compose file (optional if it's in the same dir)
COMPOSE_FILE="docker-compose.yml"

# Set variables
STACK_NAME="my-app-stack"
TEMPLATE_FILE="template_local.yaml"
CONFIG_FILE="samconfig_local.yaml"
REGION="us-west-2" 

# Optional: set project directory if needed
PROJECT_DIR="$(pwd)"

# Check if containers are running for the compose file's project
is_stack_running() {
    docker compose -f "$COMPOSE_FILE" ps -q | xargs docker inspect --format '{{.State.Running}}' 2>/dev/null | grep -q true
}

#
if is_stack_running; then
    echo "Docker Compose stack is already running."
else
    echo "Docker Compose stack is not running. Starting it now..."
    docker compose -f "$COMPOSE_FILE" up -d
    if [ $? -eq 0 ]; then
        echo "Docker Compose stack started successfully."
    else
        echo "Failed to start Docker Compose stack." >&2
        exit 1
    fi
fi


# Check if the stack exists
echo "Checking if stack '$STACK_NAME' exists in region '$REGION'..."

STACK_EXISTS=$(awslocal cloudformation describe-stacks --region "$REGION" --stack-name "$STACK_NAME" 2>/dev/null)

if [ $? -eq 0 ]; then
    echo "Stack '$STACK_NAME' exists. Deleting it..."
    #awslocal cloudformation delete-stack --region "$REGION" --stack-name "$STACK_NAME"
    samlocal delete --config-file=$CONFIG_FILE --no-prompts

    echo "Waiting for stack deletion to complete..."
    awslocal cloudformation wait stack-delete-complete --region "$REGION" --stack-name "$STACK_NAME"

    if [ $? -eq 0 ]; then
        echo "Stack '$STACK_NAME' deleted successfully."
    else
        echo "Failed to delete stack '$STACK_NAME'." >&2
        exit 1
    fi
else
    echo "Stack '$STACK_NAME' does not exist. Proceeding with deployment."
fi

# Build the SAM application
echo "Building SAM application..."
samlocal build --template-file=$TEMPLATE_FILE

if [ $? -ne 0 ]; then
    echo "SAM build failed." >&2
    exit 1
fi

# Deploy the SAM application
echo "Deploying SAM application..."
samlocal deploy --config-file=$CONFIG_FILE --no-confirm-changeset

if [ $? -eq 0 ]; then
    echo "SAM application deployed successfully."
else
    echo "SAM deployment failed." >&2
    exit 1
fi





