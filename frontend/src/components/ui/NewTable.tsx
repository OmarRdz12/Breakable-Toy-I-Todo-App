import { useAppDispatch, useAppSelector } from "../../app/hooks"
import { controlUpdate } from "../../features/forms/modalSlice"
import { updateSelected } from "../../features/tasks/taskSlice"
import UpdateModal from "../todo/UpdateModal"
import BaseButton from "./Buttons"
import { Task } from "./types"
import { MdDelete, MdEdit } from "react-icons/md"

interface TableBaseProps {
    headers: string[]
    rows: Task[]
    fetchData(): Promise<void>
}

const NewTable = ({ headers, rows, fetchData }: TableBaseProps) => {
    const updateModal = useAppSelector(state => state.update.open)
    const dispatch = useAppDispatch()

    const loadDataModal = (task: Task) => {
        dispatch(updateSelected(task))
        dispatch(controlUpdate(true))
    }

    return (
        <div className="w-full mt-2 flex justify-center">
            <table className="w-11/12 table-auto border-collapse">
                <thead>
                    <tr className="text-left bg-gray-100 text-sm">
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
                                <td className="py-3">{row.name}</td>
                                <td className="py-3">{row.priority}</td>
                                <td className="py-3">{row.dueDate}</td>
                                <td className="flex gap-1 py-3">
                                    <BaseButton
                                        text="delete"
                                        className="hover:!text-red-500 hover:!border-red-500"
                                        shape="circle"
                                        icon={<MdDelete />}
                                        toolTip
                                        htmlType="button"
                                    />
                                    <BaseButton
                                        text="edit"
                                        shape="circle"
                                        icon={<MdEdit />}
                                        toolTip
                                        htmlType="button"
                                        onClick={() => loadDataModal(row)}
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