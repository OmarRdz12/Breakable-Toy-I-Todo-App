import { PaginationProps, Space, TableProps } from "antd"
import { useAppDispatch, useAppSelector } from "../../app/hooks"
import { changePage } from "../../features/tasks/paginationSlice"
import BasePagination from "../ui/Pagination"
import { Task } from "../ui/types"
import NewTable from "../ui/NewTable"

interface DataViewerProps {
    fetchData(): Promise<void>
}

const DataViewer = ({ fetchData }: DataViewerProps) => {
    const tasks = useAppSelector(state => state.tasks)
    const totalRecords = useAppSelector(state => state.pagination.total)
    const currentPage = useAppSelector(state => state.pagination.current)
    const dispatch = useAppDispatch()

    const onPageChange: PaginationProps['onChange'] = async (page) => {
        dispatch(changePage(page))
        await fetchData()
    }

    return (
        <div className="flex flex-col items-center w-full">
            <NewTable fetchData={fetchData} rows={tasks.data} headers={["Name", "Priority", "Due Date", "Actions"]} />
            <BasePagination onChange={onPageChange} current={currentPage} total={totalRecords} align={"center"} className={"mt-10 w-11/12"} />
        </div>
    )
}

export default DataViewer