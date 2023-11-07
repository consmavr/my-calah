
import "./board.css"

interface Props {
    pit1: number;
    pit2: number;
    p1: number[];
    p2: number[];
    activePlayer?: string;
    onClick(pitNumber: number): void;
}

// https://codepen.io/ChiliTomatoNoodle/pen/LOaPmy
export const Board = (props: Props) => {
    const isGameOver = !props.activePlayer;
    const isP1Turn = props.activePlayer === "PLAYER_1"
    return (
        <div style={{
            display: "flex",
            alignItems: "center",
            gap: 15
        }}>
            <div style={{ fontSize: "3em", visibility: !isP1Turn || isGameOver ? "hidden" : undefined }}>→</div>
            <div className="board">
                <div className="section-p1 endsection">
                    <div className="pot" >{props.pit1}</div>
                </div>
                <div className="midsection">
                    <div className="section-p1 midrow topmid">
                        {props.p1.map((seeds: number, index: number) => (
                            <div className={`pot ${isP1Turn && seeds !== 0 ? "active-pit" : ""}`} onClick={() => props.activePlayer === "PLAYER_1" && props.onClick(props.p1.length - 1 - index)}>{seeds}</div>
                        ))}

                    </div>
                    <div className="section-p2 midrow botmid">
                        {props.p2.map((seeds: number, index: number) => (
                            <div className={`pot ${!isP1Turn && seeds !== 0 ? "active-pit" : ""}`} onClick={() => props.activePlayer === "PLAYER_2" && props.onClick(index)}>{seeds}</div>
                        ))}
                    </div>
                </div>
                <div className="section-p2 endsection">
                    <div className="pot" >{props.pit2}</div>
                </div>
            </div>
            <div style={{ fontSize: "3em", visibility: isP1Turn || isGameOver ? "hidden" : undefined }}>←</div>

        </div>
    )
}