#!/bin/bash

# Navigate to your project directory
cd /d/Trac/Uni/programming_exercise/week1/tttbasic

# Classpath to your compiled classes
CP="target/classes"

echo ""
echo "=========================================="
echo "Stress Testing Tic-Tac-Toe Server"
echo "=========================================="

# Test with different numbers of concurrent clients
for n in 5 10 20 50 100 10000; do
  echo ""
  echo "=========================================="
  echo "Testing with $n concurrent clients..."
  echo "=========================================="
  
  # Spawn n clients in parallel
  for i in $(seq 1 $n); do
    (
      java -cp "$CP" vgu.trac.ttt.basic.Client > /dev/null 2>&1
    ) &
  done
  
  # Wait for all background processes to complete
  wait
  
  # Wait 2 seconds between tests
  sleep 2
  
  echo "✓ Results for $n clients completed."
  echo ""
done

echo ""
echo "=========================================="
echo "Stress test complete!"
echo "=========================================="
