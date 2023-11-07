interface IComponentProps {
    gameStatus: string;
}
export const GameResult: React.FC<IComponentProps> = ({ gameStatus }) => {
    const text = gameStatus == "DRAW" ? "It's a draw 🎨🎨🎨"
        : gameStatus == "PLAYER_ONE_WIN" ? "🎉🎉🎉 Winner: Player 1 🎉🎉🎉"
            : gameStatus == "PLAYER_TWO_WIN" ? "🎉🎉🎉 Winner: Player 2 🎉🎉🎉"
                : undefined

    if (!text) {
        return
    }
    return <div><h1>{text}</h1></div>
}

