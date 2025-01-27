import { useEffect, useState } from "react"
import { useAppDispatch, useAppSelector } from "../../app/hooks"
import { controlUpdate } from "../../features/forms/modalSlice"
import { updateSelected } from "../../features/tasks/taskSlice"
import UpdateModal from "../todo/UpdateModal"
import BaseButton from "./Buttons"
import BaseCheckbox from "./Checkbox"
import { Task } from "./types"
import { MdDelete, MdEdit } from "react-icons/md"
import axios from "axios"
import { toast } from "sonner"

interface TableBaseProps {
    headers: string[]
    rows: Task[]
    fetchData(): Promise<void>
    columnSelector?: boolean
}

const NewTable = ({ headers, rows, fetchData, columnSelector = false }: TableBaseProps) => {
    const apiUrl = import.meta.env.VITE_API_URL || "http://localhost:9090"
    const updateModal = useAppSelector(state => state.update.open)
    const dispatch = useAppDispatch()
    const [stateCheckbox, setStateCheckbox] = useState<{ [key: number]: boolean }>({})
    useEffect(() => {
        const initialState = rows.reduce(
            (acc, row) => {
                acc[row.id] = row.state
                return acc
            },
            {} as { [key: number]: boolean }
        )
        setStateCheckbox(initialState)
    }, [rows])

    const loadDataModal = (task: Task) => {
        dispatch(updateSelected(task))
        dispatch(controlUpdate(true))
    }

    const onDelete = async (id: number) => {
        try {
            dispatch(controlUpdate(false))
            await axios.delete(`${apiUrl}/todos/${id}`)
            await fetchData()
            toast.warning('Task has been deleted')
        } catch (error) {
            toast.error('Something went wrong')
        }
    }

    const changeStateCheckbox = (id: number, state: boolean) => {
        setStateCheckbox((prevState) => ({
            ...prevState,
            [id]: state
        }))
    }

    return (
        <div className="w-full mt-2 flex justify-center">
            <table className="w-11/12 table-auto border-collapse">
                <thead>
                    <tr className="text-left bg-gray-100 text-sm">
                        {
                            columnSelector &&
                            <th><BaseCheckbox columnSelector={true} fetchData={fetchData} setChecked={() => { }} /></th>
                        }
                        {
                            headers.map((header, key) => (
                                <th className="py-3 pl-1" key={key}> {header} </th>
                            ))
                        }
                    </tr>
                </thead>
                <tbody>
                    {
                        rows.map((row) => (
                            <tr className="border-b" key={row.id}>
                                <td className="py-3"><BaseCheckbox setChecked={changeStateCheckbox} fetchData={fetchData} id={row.id} originChecked={stateCheckbox[row.id]} /></td>
                                <td className="py-3">{row.name}</td>
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