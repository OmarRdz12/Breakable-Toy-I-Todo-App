import { PaginationProps } from "antd"
import { useAppDispatch, useAppSelector } from "../../../app/hooks"
import { changePage } from "../../../features/tasks/paginationSlice"
import BasePagination from "../../ui/Pagination/Pagination"
import NewTable, { HeadersProps } from "../../ui/NewTable"

interface DataViewerProps {
    fetchData(): Promise<void>
}

const DataViewer = ({ fetchData }: DataViewerProps) => {
    const tasks = useAppSelector(state => state.tasks.data)
    const totalRecords = useAppSelector(state => state.pagination.total)
    const currentPage = useAppSelector(state => state.pagination.current)
    const dispatch = useAppDispatch()

    const headers: HeadersProps[] = [
        { title: "Name", sorter: false },
        { title: "Priority", sorter: true, titleSorter: "prioritySort" },
        { title: "Due Date", sorter: true, titleSorter: "dueDateSort" },
        { title: "Actions", sorter: false }
    ]

    const onPageChange: PaginationProps['onChange'] = async (page) => {
        dispatch(changePage(page))
        await fetchData()
    }

    return (
        <div className="flex flex-col items-center w-full">
            <NewTable fetchData={fetchData} rows={tasks} columnSelector headers={headers} />
            <BasePagination onChange={onPageChange} current={currentPage} total={totalRecords} align={"center"} className={"mt-10 w-11/12"} />
        </div>
    )
}

export default DataViewer