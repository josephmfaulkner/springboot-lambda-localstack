#!/bin/bash
set -x #echo on

# Define the path to your Docker Compose file
COMPOSE_FILE="docker-compose.yml"

# Function to check if the stack has running containers
is_stack_running() {
    docker compose -f "$COMPOSE_FILE" ps -q | xargs -r docker inspect --format '{{.State.Running}}' 2>/dev/null | grep -q true
}

# Main logic
if is_stack_running; then
    echo "Docker Compose stack is running. Bringing it down..."
    docker compose -f "$COMPOSE_FILE" down
    if [ $? -eq 0 ]; then
        echo "Docker Compose stack stopped successfully."
    else
        echo "Failed to stop Docker Compose stack." >&2
        exit 1
    fi
else
    echo "Docker Compose stack is not running. Nothing to do."
fi