import { PaginationProps, Space, TableProps } from "antd"
import { useAppDispatch, useAppSelector } from "../../app/hooks"
import { changePage } from "../../features/tasks/paginationSlice"
import BaseTable from "../ui/BaseTable"
import BasePagination from "../ui/Pagination"
import { Task } from "../ui/types"

interface DataViewerProps {
    fetchData(): Promise<void>
}

const DataViewer = ({ fetchData }: DataViewerProps) => {
    const tasks = useAppSelector(state => state.tasks)
    const totalRecords = useAppSelector(state => state.pagination.total)
    const currentPage = useAppSelector(state => state.pagination.current)
    const dispatch = useAppDispatch()

    const columns: TableProps<Task>['columns'] = [
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Priority',
            dataIndex: 'priority',
            key: 'priority',
        },
        {
            title: 'Due date',
            dataIndex: 'dueDate',
            key: 'dueDate',
        },

        {
            title: 'Action',
            key: 'action',
            render: (_) => (
                <Space size="middle">
                    <a>Edit </a>
                    <a>Delete</a>
                </Space>
            ),
        },
    ]

    const onPageChange: PaginationProps['onChange'] = async (page, pageSize) => {
        dispatch(changePage(page))
        await fetchData()
    }

    return (
        <>
            <BaseTable data={tasks.data} columns={columns} />
            <BasePagination onChange={onPageChange} current={currentPage} total={totalRecords} align={"center"} className={"mt-10"} />
        </>
    )
}

export default DataViewer