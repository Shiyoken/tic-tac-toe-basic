# Multi-User Single Thread: Secure Tic-Tac-Toe

This directory contains a specialized, single-threaded, multi-user implementation of a Tic-Tac-Toe client-server architecture with significant security enhancements to prevent cheating and network attacks.

## Core Files
* **`ModernServer.java`**: The secure server that handles client connections, validates game logic, and enforces strict security protocols.
* **`ModernClient.java`**: The secure client that communicates with the server, respecting the required cryptographic payload structure.
* **`SecureServer.java` / `SecureClient.java`**: The foundational files from which the modern architecture was built.

## Security Features Implemented

The `ModernServer` and `ModernClient` implement several advanced security layers to protect the integrity of the game:

### 1. Cryptographic Tamper-Proofing (HMAC-SHA256)
Every critical piece of data (Board state, Nonce, Timestamp) sent over the network is hashed using `HMAC-SHA256` with a secret key. This prevents "Man-in-the-Middle" attackers from modifying the board (e.g., placing an 'X' in a winning spot) because the server verifies the hashes upon receiving the payload.

### 2. Anti-Replay Attack System (Nonces)
To prevent a hacker from recording a valid move and re-sending it multiple times to cheat, a **Nonce** ("Number used ONCE") system is implemented using `java.security.SecureRandom`.
* **In-Memory Database**: The server uses a thread-safe `ConcurrentHashMap` to store every nonce it issues.
* **Burn on Use**: When the client sends a message, the server immediately checks if the nonce exists. If it does, it rejects the move as a replay attack. If it doesn't, it processes the move and burns the nonce by adding it to the database.
* **Fresh Tickets**: If a user makes an invalid move (e.g., choosing an occupied cell), the server issues a brand new nonce and timestamp to give them a fresh opportunity without reusing old data.

### 3. Background Memory Cleanup (Cron Task)
To prevent the `ConcurrentHashMap` database from growing infinitely and causing a memory leak, a background "Cron Task" was built using Java's `ScheduledExecutorService`. 
* A background thread wakes up every 5 seconds.
* It scans the database and automatically deletes any nonces that are older than 10 seconds, keeping the server's memory perfectly clean and efficient.

### 4. Enforced Time Limits (Timestamp Validation)
The server enforces a strict 10-second limit for players to make their move.
* The server attaches a `timestamp` to its message.
* When the client replies, the server compares the returned timestamp against the current server time (`Instant.now().toEpochMilli()`).
* If the difference is greater than 10 seconds (10,000 milliseconds), the server rejects the move with an `ERROR_TIMEOUT` and kicks the player out.
