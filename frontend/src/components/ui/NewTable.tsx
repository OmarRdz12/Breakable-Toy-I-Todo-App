import { useAppDispatch, useAppSelector } from "../../app/hooks"
import { controlUpdate } from "../../features/forms/modalSlice"
import { updateSelected } from "../../features/tasks/taskSlice"
import UpdateModal from "../todo/UpdateModal"
import BaseButton from "./Buttons/Buttons"
import BaseCheckbox from "./Checkbox"
import { Task } from "./types"
import { MdDelete, MdEdit } from "react-icons/md"
import axios from "axios"
import { toast } from "sonner"
import { MdArrowDropUp, MdArrowDropDown } from "react-icons/md";
import { onChangeSort } from "../../features/forms/filterSlice"
import { differenceInDays, parseISO } from "date-fns"

type FilterStateB = {
    prioritySort?: "asc" | "desc" | ""
    dueDateSort?: "asc" | "desc" | ""
}

export interface HeadersProps {
    title: string
    sorter?: boolean
    titleSorter?: keyof FilterStateB
}

interface TableBaseProps {
    headers: HeadersProps[]
    rows: Task[]
    fetchData(): Promise<void>
    columnSelector?: boolean
}

const NewTable = ({ headers, rows, fetchData, columnSelector = false }: TableBaseProps) => {
    const apiUrl = import.meta.env.VITE_API_URL || "http://localhost:9090"
    const updateModal = useAppSelector(state => state.update.open)
    const sorts = useAppSelector(state => state.sorts)
    const dispatch = useAppDispatch()


    const handleChange = (name: string | undefined) => {
        if (name === 'prioritySort')
            dispatch(onChangeSort({ [name]: sorts.prioritySort === "" ? "asc" : (sorts.prioritySort === "asc" ? "desc" : "") }))
        if (name === 'dueDateSort')
            dispatch(onChangeSort({ [name]: sorts.dueDateSort === "" ? "asc" : (sorts.dueDateSort === "asc" ? "desc" : "") }))
    }

    const loadDataModal = (task: Task) => {
        dispatch(updateSelected(task))
        dispatch(controlUpdate(true))
    }

    const onDelete = async (id: number) => {
        try {
            dispatch(controlUpdate(false))
            await axios.delete(`${apiUrl}/todos/${id}`)
            fetchData()
            toast.warning('Task has been deleted')
        } catch (error) {
            toast.error('Something went wrong')
        }
    }

    return (
        <div className="w-full mt-2 flex justify-center">
            <div className="w-11/12 rounded-xl overflow-hidden shadow-md border-x border-t border-black">
                <table className="w-full table-auto">
                    <thead>
                        <tr className="text-left bg-blue-400 text-black text-lg font-semibold border-b border-black">
                            {
                                columnSelector &&
                                <th className="text-center rounded-tl-xl">
                                    <BaseCheckbox columnSelector={true} fetchData={fetchData} />
                                </th>
                            }
                            {
                                headers.map((header, key) => (
                                    <th
                                        className={`py-3 pl-1 ${!columnSelector && key === 0 ? 'rounded-tl-xl' : ''} ${key === headers.length - 1 ? 'rounded-tr-xl' : ''}`}
                                        key={key}
                                    >
                                        <div className="flex items-center gap-2">
                                            {header.title}
                                            {
                                                header.sorter &&
                                                <div className="flex flex-col hover:cursor-pointer text-sm" onClick={() => handleChange(header.titleSorter)}>
                                                    <MdArrowDropUp className={`${header.titleSorter && sorts[header.titleSorter] === 'asc' && 'text-blue-800'}`} />
                                                    <MdArrowDropDown className={`${header.titleSorter && sorts[header.titleSorter] === 'desc' && 'text-blue-800'}`} />
                                                </div>
                                            }
                                        </div>
                                    </th>
                                ))
                            }
                        </tr>
                    </thead>
                    <tbody>
                        {
                            rows.map((row) => (
                                <tr className={`border-b border-black ${row.dueDate && differenceInDays(parseISO(row.dueDate), new Date()) <= 7 ? 'bg-red-200' : row.dueDate && differenceInDays(parseISO(row.dueDate), new Date()) <= 14 ? 'bg-yellow-200' : row.dueDate !== null && 'bg-green-200'}`} key={row.id}>
                                    <td className="flex justify-center items-center">
                                        <BaseCheckbox fetchData={fetchData} id={row.id} originChecked={row.state} />
                                    </td>
                                    <td className={`py-3 ${row.state === true && 'line-through'}`}>{row.name}</td>
                                    <td className="py-3">{row.priority}</td>
                                    <td className="py-3">{row.dueDate}</td>
                                    <td className="flex gap-1 py-3">
                                        <BaseButton
                                            text="delete"
                                            className={`${!row.state && 'hover:!text-red-500 hover:!border-red-500'} border border-black`}
                                            shape="circle"
                                            icon={<MdDelete />}
                                            toolTip
                                            htmlType="button"
                                            disabled={row.state}
                                            onClick={() => onDelete(row.id)}
                                            id="delete"
                                        />
                                        <BaseButton
                                            text="edit"
                                            shape="circle"
                                            icon={<MdEdit />}
                                            toolTip
                                            htmlType="button"
                                            onClick={() => loadDataModal(row)}
                                            disabled={row.state}
                                            id="edit"
                                            className="border border-black"
                                        />
                                    </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
            {
                updateModal &&
                <UpdateModal fetchData={fetchData} />
            }
        </div>
    )

}

export default NewTable