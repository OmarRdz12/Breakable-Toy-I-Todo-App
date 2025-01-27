import { DatePickerProps } from "antd"
import InputDate from "../ui/InputDate"
import InputSelect from "../ui/InputSelect"
import InputText from "../ui/InputText"
import BaseModal from "../ui/Modal"
import { useState } from "react"
import axios from "axios"
import dayjs, { Dayjs } from 'dayjs'
import { useAppDispatch, useAppSelector } from "../../app/hooks"
import { controlUpdate } from "../../features/forms/modalSlice"
import { toast } from "sonner"

interface UpdateModalProps {
    fetchData(): Promise<void>
}

interface UpdateForm {
    name: string
    priority: string
    dueDate: string | number | Dayjs | Date | null | undefined
}

const UpdateModal = ({ fetchData }: UpdateModalProps) => {
    const apiUrl = import.meta.env.VITE_API_URL || "http://localhost:9090"
    const updateModal = useAppSelector(state => state.update.open)
    const task = useAppSelector(state => state.selectedTask)
    const dispatch = useAppDispatch()
    const [formData, setFormData] = useState<UpdateForm>({ name: task.name, priority: task.priority, dueDate: task.dueDate ? dayjs(task.dueDate) : null })

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value })
    }

    const handleChange = (value: string, name: string) => {
        setFormData({ ...formData, [name]: value })
    }

    const onChange: DatePickerProps['onChange'] = (date) => {
        setFormData({ ...formData, ["dueDate"]: date ? date.format("YYYY-MM-DD") : null })
    }

    const onSubmit = async () => {
        try {
            dispatch(controlUpdate(false))
            await axios.put(`${apiUrl}/todos/${task.id}`, formData)
            await fetchData()
            toast.success("Task has been updated successfully")
        } catch (error) {
            toast.error("Something went wrong")
        }
    }

    const closeModal = () => {
        dispatch(controlUpdate(false))
    }

    return (
        <>
            <BaseModal onSubmit={onSubmit} closeModal={closeModal} openModal={updateModal} text="Save" title="Update To Do" >
                <form className="w-full flex flex-col p-4 gap-2">
                    <InputText
                        placeholder="Escribe aqui"
                        name="name"
                        id="name"
                        type="text"
                        size="large"
                        label="Name"
                        value={formData.name}
                        maxLength={120}
                        showCount
                        onChange={handleInputChange}
                        required
                    />
                    <InputSelect
                        name="priority"
                        label="Priority"
                        id="priority"
                        onChange={(value) => handleChange(value, "priority")}
                        options={[
                            { label: 'High', value: "HIGH" },
                            { label: 'Medium', value: "MEDIUM" },
                            { label: 'Low', value: "LOW" },
                        ]}
                        value={formData.priority}
                        defaultValue=""
                        size="large"
                        required
                    />
                    <InputDate
                        label="Due date"
                        id="dueDate"
                        onChange={onChange}
                        size="large"
                        name="dueDate"
                        value={formData.dueDate ? dayjs(formData.dueDate) : null}
                    />
                </form>
            </BaseModal>
        </>
    )
}

export default UpdateModal