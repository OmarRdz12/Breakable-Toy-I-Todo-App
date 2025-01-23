import { Table, TableProps } from "antd"
import { Task } from "./types"

interface BaseTableProps {
    columns: TableProps<Task>['columns']
    data: Task[]
}

const BaseTable = ({ columns, data }: BaseTableProps) => {
    return (
        <Table<Task> columns={columns} dataSource={data} pagination={false} />
    )
}

export default BaseTable