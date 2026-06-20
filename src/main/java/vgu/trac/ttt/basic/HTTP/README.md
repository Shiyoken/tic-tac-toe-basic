# HTTP Tic-Tac-Toe Server

A simple HTTP-based Tic-Tac-Toe game server built using Java's built-in `com.sun.net.httpserver.HttpServer`.  
This is a transition from the custom Socket-based protocol to a standard HTTP protocol that can be tested directly from a web browser or terminal.

---

## Project Structure

```
HTTP/
├── HTTPServer.java   # The HTTP Server (handles /start and /play endpoints)
├── HTTPClient.java   # The Java HTTP Client (console-based game interface)
└── README.md         # This file
```

---

## How It Works (Flow)

```
Client                          Server
  |                               |
  |--- GET /start --------------> |  Server returns an empty board
  |<-- {"board":"000000000",      |
  |     "status":"PLAYING"} ------|
  |                               |
  |--- POST /play (board+move) -> |  Server processes the move, plays computer move
  |<-- {"board":"010000020",      |
  |     "status":"PLAYING"} ------|
  |                               |
  |      ... repeat until ...     |
  |                               |
  |<-- {"board":"121021212",      |
  |     "status":"HUMAN_WIN"} ----|
```

### Board Format
The board is a 9-character string representing cells 1-9:
- `0` = Empty
- `1` = Human (Player 1)
- `2` = Computer (Player 2)

```
 1 | 2 | 3
-----------
 4 | 5 | 6
-----------
 7 | 8 | 9
```

### Status Values
| Status | Meaning |
|---|---|
| `PLAYING` | Game is still going |
| `HUMAN_WIN` | Player 1 wins |
| `COMPUTER_WIN` | Computer wins |
| `DRAW` | No more moves, it's a draw |
| `ERROR_FORMAT` | Move was not a valid number |
| `ERROR_EMPTY` | That cell is already occupied |
| `ERROR_INVALID` | Move is out of range |
| `END_GAME` | Player quit with `q` |

---

## How to Run

### Step 1: Build the project (from the project root)
```powershell
mvn clean package
```

### Step 2: Start the Server
```powershell
java -cp target/classes vgu.trac.ttt.basic.HTTP.HTTPServer
```
You should see:
```
Server is running on port 1234
```

### Step 3: Run the Java Client (in a new terminal)
```powershell
java -cp target/classes vgu.trac.ttt.basic.HTTP.HTTPClient
```

---

## Testing with curl (Black Box Testing)

> **Important for Windows PowerShell:** Always use `curl.exe` (not `curl`) to avoid PowerShell's built-in alias.

### Test 1: Basic endpoint
```powershell
curl.exe http://localhost:1234/
```
Expected:
```
Hello, this is the first endpoint
```

### Test 2: Start a new game
```powershell
curl.exe http://localhost:1234/start
```
Expected:
```json
{
  "board": "000000000",
  "status": "PLAYING"
}
```

### Test 3: Make a valid move (place at cell 5)
```powershell
curl.exe -X POST -H "Content-Type: application/json" -d "{\"board\":\"000000000\", \"move\":\"5\"}" http://localhost:1234/play
```
Expected:
```json
{
  "board": "000010020",
  "status": "PLAYING"
}
```

### Test 4: Invalid format (non-number move)
```powershell
curl.exe -X POST -H "Content-Type: application/json" -d "{\"board\":\"000000000\", \"move\":\"abc\"}" http://localhost:1234/play
```
Expected:
```json
{
  "board": "",
  "status": "ERROR_FORMAT"
}
```

### Test 5: Occupied cell
```powershell
curl.exe -X POST -H "Content-Type: application/json" -d "{\"board\":\"000100000\", \"move\":\"4\"}" http://localhost:1234/play
```
Expected:
```json
{
  "board": "",
  "status": "ERROR_EMPTY"
}
```

### Test 6: Wrong HTTP method (GET instead of POST on /play)
```powershell
curl.exe http://localhost:1234/play
```
Expected: HTTP 405 Method Not Allowed

---

## Notes
- The server runs on `localhost` port `1234`.
- The server is **stateless**: each request contains the full board state.
- Built with Java 25 using the built-in `com.sun.net.httpserver` package (no external libraries needed).
- Communication format is **JSON** for both requests and responses.
