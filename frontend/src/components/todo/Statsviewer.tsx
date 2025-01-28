import { useAppSelector } from "../../app/hooks"

const StatsViewer = () => {
    const stats = useAppSelector(state => state.stats)
    return (
        <div className="w-11/12 p-4 my-4 flex border-2 justify-between border-gray-200 gap-2 box-border rounded shadow">
            <div className="w-1/2 flex flex-col items-center justify-evenly">
                <h4 className="font-bold">Average time to finish tasks</h4>
                {
                    <p>{stats.allDoneStats === "00:00:00" ? "There are no statistics to show" : `${stats.allDoneStats} hh:mm:ss`}</p>
                }
            </div>
            <div className="w-1/2 flex flex-col items-center">
                <h4 className="font-bold">Average time to finish tasks by priority</h4>
                {
                    <div className="w-full flex flex-col items-center">
                        <p>High: {stats.highDoneStats === "00:00:00" ? "There are no statistics to show" : `${stats.highDoneStats} hh:mm:ss`}</p>
                        <p>Medium: {stats.mediumDoneStats === "00:00:00" ? "There are no statistics to show" : `${stats.mediumDoneStats} hh:mm:ss`}</p>
                        <p>Low: {stats.lowDoneStats === "00:00:00" ? "There are no statistics to show" : `${stats.lowDoneStats} hh:mm:ss`}</p>
                    </div>
                }
            </div>
        </div>
    )
}

export default StatsViewer