interface IComponentProps {
    p1Score: number;
    p2Score: number;
}
export const ScoreBoard: React.FC<IComponentProps> = ({ p1Score, p2Score }) => {
    return (
        <div >
            <h1 style={{ borderBottom: "2px solid white" }}>Score</h1>
            <div className="score-board">
                <div >
                    <h4>Player1</h4>
                    <h4>{p1Score}</h4>
                </div>
                <div>
                    <h4>Player2</h4>
                    <h4>{p2Score}</h4>
                </div>
            </div>
        </div>
    )
}

