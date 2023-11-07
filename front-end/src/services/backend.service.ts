import axios from "axios";


export interface Game {
    board: number[];
    gameId: bigint;
    player: string;
    status: string;
}

export const createNewGame = async () => {
    const response = await axios.post("http://localhost:8080/api/game");
    return response;
}

export const pickPot = async (potNumber: number, game: Game) => {
    const response = await axios.put(`http://localhost:8080/api/game?gameId=${game.gameId}&playerNumber=${game.player}&house=${potNumber}`)
    return response;
}

export const getExistingGame = async () => {
    const response = await axios.get(`http://localhost:8080/api/game`)
    return response;
}