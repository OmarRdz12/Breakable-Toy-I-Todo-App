import { ConfigProvider, Pagination } from "antd"

interface PaginationProps {
    current: number,
    total: number,
    align?: "start" | "center" | "end" | undefined
    onChange: (page: number, pageSize: number) => void
    className?: string
}

const BasePagination = ({ current, total, onChange, align, className }: PaginationProps) => {
    return (
        <ConfigProvider
            theme={{
                token: {
                    colorBorder: "#000000",
                    colorPrimary: "#000000"
                },
                components: {
                    Pagination: {
                        itemActiveBg: "#8ec5ff"
                    }
                }
            }}
        >
            <Pagination
                current={current}
                total={total}
                onChange={onChange}
                align={align}
                className={`${className}`}
                showTotal={(total, range) => `${range[0]}-${range[1]} of ${total} items`}
            />
        </ConfigProvider>
    )
}

export default BasePagination