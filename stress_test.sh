#!/bin/bash
# test_server.sh - Stress test your Tic-Tac-Toe server

PORT=8080
HOST="localhost"
NUM_CLIENTS=${1:-100}  # Default 100 clients, or use argument

echo "=========================================="
echo "Stress Testing Thread Pool Server"
echo "Port: $PORT, Clients: $NUM_CLIENTS"
echo "=========================================="

# Spawn concurrent clients
for i in $(seq 1 $NUM_CLIENTS); do
  (
    {
      sleep 0.5          # Wait for server greeting
      echo "5"           # Send move to position 5
      sleep 1            # Keep connection alive
      echo "q"           # Quit gracefully
    } | timeout 10 nc localhost $PORT > /dev/null 2>&1
  ) &
  
  # Show progress every 100 connections
  if [ $((i % 100)) -eq 0 ]; then
    echo "[Progress] Spawned $i/$NUM_CLIENTS clients"
  fi
done

echo "All $NUM_CLIENTS clients spawned!"
echo "Waiting for all connections to complete..."
wait

echo "=========================================="
echo "Test completed at $(date)"
echo "=========================================="
