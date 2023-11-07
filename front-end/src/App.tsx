import { useEffect, useState } from 'react'
import './App.css'
import { Board } from './components/Board/board'
import { Game, createNewGame, pickPot, getExistingGame } from './services/backend.service'
import { ScoreBoard } from './components/ScoreBoard/score-board'
import { GameResult } from './components/GameResult/game-result'


function App() {
  const [game, setGame] = useState<Game>()

  const onPitClick = async (pitNumber: number) => {
    if (game) {
      const response = await pickPot(pitNumber, game);
      setGame(response.data)
    }

  }

  const createGame = async () => {
    const response = await createNewGame();
    setGame(response.data);
  }

  useEffect(() => {

    const loadGame = async () => {
      const response = await getExistingGame();
      console.log(response.data)
      setGame(response.data);
    }
    loadGame();
  }, [])

  return (
    <div className="container">
      {game && <ScoreBoard p1Score={game.board[6]} p2Score={game.board[13]} />}
      {game && <Board
        activePlayer={game.status === "ACTIVE" ? game.player : undefined}
        pit1={game.board[6]}
        p1={game.board.slice(0, 6).reverse()}
        pit2={game.board[13]}
        p2={game.board.slice(7, 13)}
        onClick={onPitClick} />}
      {game && <GameResult gameStatus={game.status} />}

      <button onClick={createGame} className="create-button">NEW GAME</button>
    </div>
  )
}

export default App
