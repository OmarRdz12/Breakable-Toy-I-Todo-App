import { DatePickerProps } from "antd"
import InputDate from "../ui/InputDate"
import InputSelect from "../ui/InputSelect"
import InputText from "../ui/InputText"
import BaseModal from "../ui/Modal/Modal"
import { useState } from "react"
import axios from "axios"
import dayjs, { Dayjs } from 'dayjs'
import { useAppDispatch, useAppSelector } from "../../app/hooks"
import { controlCreate } from "../../features/forms/modalSlice"
import { toast } from "sonner"

interface CreationModalProps {
    fetchData(): Promise<void>;
}

interface CreationForm {
    name: string
    priority: string
    dueDate: string | number | Dayjs | Date | null | undefined
}

const CreationModal = ({ fetchData }: CreationModalProps) => {
    const apiUrl = import.meta.env.VITE_API_URL || "http://localhost:9090"
    const creationModal = useAppSelector(state => state.creation.open)
    const dispatch = useAppDispatch()
    const [formData, setFormData] = useState<CreationForm>({ name: "", priority: "", dueDate: null })

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value })
    }

    const handleChange = (value: string, name: string) => {
        setFormData({ ...formData, [name]: value })
    }

    const onChange: DatePickerProps['onChange'] = (date) => {
        setFormData({ ...formData, ["dueDate"]: date.format("YYYY-MM-DD") })
    }

    const onSubmit = async () => {
        if(formData.name === "" || formData.priority === ""){
            toast.error("Fill in all required fields")
            return
        }
        try {
            dispatch(controlCreate(false))
            const data = await axios.post(`${apiUrl}/todos`, formData)
            fetchData()
            setFormData({ name: "", priority: "", dueDate: "" })
            toast.success('Task has been created', {
                description: `${data?.data.dueDate}`,
            })
        } catch (error) {
            toast.error('Something went wrong')
        }
    }

    const closeModal = () => {
        dispatch(controlCreate(false))
        setFormData({ name: "", priority: "", dueDate: "" })
    }

    return (
        <>
            <BaseModal onSubmit={onSubmit} closeModal={closeModal} openModal={creationModal} text="Save" title="New To Do" >
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

export default CreationModal