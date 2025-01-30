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
            <table className="w-11/12 table-auto border-collapse border-black">
                <thead>
                    <tr className="text-left bg-zinc-900 text-white text-sm font-semibold">
                        {
                            columnSelector &&
                            <th className="text-center">
                                <BaseCheckbox columnSelector={true} fetchData={fetchData} />
                            </th>
                        }
                        {
                            headers.map((header, key) => (
                                <th className="py-3 pl-1" key={key}>
                                    <div className="flex items-center gap-2">
                                        {header.title}
                                        {
                                            header.sorter &&
                                            <div className="flex flex-col hover:cursor-pointer text-sm" onClick={() => handleChange(header.titleSorter)}>
                                                <MdArrowDropUp className={`${header.titleSorter && sorts[header.titleSorter] === 'asc' && 'text-blue-500'}`} />
                                                <MdArrowDropDown className={`${header.titleSorter && sorts[header.titleSorter] === 'desc' && 'text-blue-500'}`} />
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
                            <tr className={`border-b ${row.dueDate && differenceInDays(parseISO(row.dueDate), new Date()) <= 7 ? 'bg-red-300': row.dueDate && differenceInDays(parseISO(row.dueDate), new Date()) <= 14 ? 'bg-yellow-300' : row.dueDate !== null && 'bg-green-300'  }`} key={row.id}>
                                <td className="flex justify-center items-center">
                                    <BaseCheckbox fetchData={fetchData} id={row.id} originChecked={row.state} />
                                </td>
                                <td className={`py-3 ${row.state === true && 'line-through'}`}>{row.name}</td>
                                <td className="py-3">{row.priority}</td>
                                <td className="py-3">{row.dueDate}</td>
                                <td className="flex gap-1 py-3">
                                    <BaseButton
                                        text="delete"
                                        className={`${!row.state && 'hover:!text-red-500 hover:!border-red-500'} `}
                                        shape="circle"
                                        icon={<MdDelete />}
                                        toolTip
                                        htmlType="button"
                                        disabled={row.state}
                                        onClick={() => onDelete(row.id)}
                                    />
                                    <BaseButton
                                        text="edit"
                                        shape="circle"
                                        icon={<MdEdit />}
                                        toolTip
                                        htmlType="button"
                                        onClick={() => loadDataModal(row)}
                                        disabled={row.state}
                                    />
                                </td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
            {
                updateModal &&
                <UpdateModal fetchData={fetchData} />
            }
        </div>
    )
}

export default NewTable