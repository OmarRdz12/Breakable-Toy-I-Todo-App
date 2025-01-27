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
            <form className="w-11/12 p-4 my-4 flex flex-col border-2 border-gray-200 gap-2 box-border rounded shadow " onSubmit={handleSubmit}>
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
                <div className="w-full flex justify-center">
                    <BaseButton
                        htmlType="submit"
                        text="Search"
                        size="large"
                        className="w-1/2 bg-zinc-900 text-white shadow hover:!bg-zinc-700 
                        hover:!border-zinc-700 hover:!text-white"
                    />
                </div>
            </form>
        </>
    )
}

export default FilterForm