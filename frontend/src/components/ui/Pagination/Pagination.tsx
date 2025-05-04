import { Pagination } from "antd"

interface PaginationProps {
    current: number,
    total: number,
    align?: "start" | "center" | "end" | undefined
    onChange: (page: number, pageSize: number) => void
    className?: string
}

const BasePagination = ({ current, total, onChange, align, className }: PaginationProps) => {
    return (
        <Pagination
            current={current}
            total={total}
            onChange={onChange}
            align={align}
            className={`${className}`}
            showTotal={(total, range) => `${range[0]}-${range[1]} of ${total} items`}
        />
    )
}

export default BasePagination