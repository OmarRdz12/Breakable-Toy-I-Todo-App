import InputSelect from "../ui/InputSelect"
import InputText from "../ui/InputText"
import { useAppDispatch } from "../../app/hooks"
import { onChange } from "../../features/forms/filterSlice"
import BaseButton from "../ui/Buttons"

interface FilterProps {
    fetchData(): Promise<void>;
}

const FilterForm = ({ fetchData }: FilterProps) => {
    const dispatch = useAppDispatch()

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        dispatch(onChange({ [e.target.name]: e.target.value }))
    }

    const handleChange = (value: string, name: string) => {
        dispatch(onChange({ [name]: value }))

    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()
        await fetchData()
    }

    return (
        <>
            <form className="w-full flex flex-col gap-2 box-border" onSubmit={handleSubmit}>
                <InputText
                    placeholder="Escribe aqui"
                    name="name"
                    id="name"
                    type="text"
                    size="large"
                    label="Name"
                    maxLength={120}
                    showCount
                    onChange={handleInputChange}
                />
                <InputSelect
                    name="priority"
                    label="Priority"
                    id="priority"
                    onChange={(value) => handleChange(value, "priority")}
                    options={[
                        { label: 'All', value: "all" },
                        { label: 'High', value: "HIGH" },
                        { label: 'Medium', value: "MEDIUM" },
                        { label: 'Low', value: "LOW" },
                    ]}
                    defaultValue="all"
                    size="large"
                />
                <InputSelect
                    name="state"
                    label="State"
                    id="state"
                    onChange={(value) => handleChange(value, "state")}
                    options={[
                        { label: 'All', value: "all" },
                        { label: 'Done', value: "true" },
                        { label: 'Undone', value: "false" },
                    ]}
                    defaultValue="all"
                    size="large"
                />
                <BaseButton htmlType="submit" text="Search" size="large" className="w-1/4 bg-purple-500 shadow" />
            </form>
        </>
    )
}

export default FilterForm