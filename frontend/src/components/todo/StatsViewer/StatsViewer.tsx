import { useAppSelector } from "../../../app/hooks"

const StatsViewer = () => {
    const stats = useAppSelector(state => state.stats)
    const allTasks: string[] = stats.allDoneStats.split(":")
    const highTasks: string[] = stats.highDoneStats.split(":")
    const mediumTasks: string[] = stats.mediumDoneStats.split(":")
    const lowTaks: string[] = stats.lowDoneStats.split(":")
    return (
        <div className="w-11/12 p-4 my-4 flex border-2 justify-between border-gray-200 gap-2 box-border rounded shadow">
            <div className="w-1/2 flex flex-col items-center justify-evenly">
                <h4 className="font-bold">Average time to finish tasks</h4>
                {
                    <p>{stats.allDoneStats === "00:00:00" ? "There are no statistics to show" : `${allTasks[0]} hours, ${allTasks[1]} minutes and ${allTasks[2]} seconds`}</p>
                }
            </div>
            <div className="w-1/2 flex flex-col items-center">
                <h4 className="font-bold">Average time to finish tasks by priority</h4>
                {
                    <div className="w-full flex flex-col items-center">
                        <p>High: {stats.highDoneStats === "00:00:00" ? "There are no statistics to show" : `${highTasks[0]} hours, ${highTasks[1]} minutes and ${highTasks[2]} seconds`}</p>
                        <p>Medium: {stats.mediumDoneStats === "00:00:00" ? "There are no statistics to show" : `${mediumTasks[0]} hours, ${mediumTasks[1]} minutes and ${mediumTasks[2]} seconds`}</p>
                        <p>Low: {stats.lowDoneStats === "00:00:00" ? "There are no statistics to show" : `${lowTaks[0]} hours, ${lowTaks[1]} minutes and ${lowTaks[2]} seconds`}</p>
                    </div>
                }
            </div>
        </div>
    )
}

export default StatsViewer