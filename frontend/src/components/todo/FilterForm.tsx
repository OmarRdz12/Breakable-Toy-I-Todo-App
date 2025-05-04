import InputSelect from "../ui/InputSelect"
import InputText from "../ui/InputText"
import { useAppDispatch } from "../../app/hooks"
import { onChange } from "../../features/forms/filterSlice"
import BaseButton from "../ui/Buttons/Buttons"
import { FaSearch } from "react-icons/fa"
import { useFetchTodos } from "../../hooks/useFetchTodos"

const FilterForm = () => {
    const dispatch = useAppDispatch()
    const { fetchTodos } = useFetchTodos()

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        dispatch(onChange({ [e.target.name]: e.target.value }))
    }

    const handleChange = (value: string, name: string) => {
        dispatch(onChange({ [name]: value }))
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()
        await fetchTodos()
    }

    return (
        <form className="w-11/12 p-4 flex flex-col gap-2 box-border" onSubmit={handleSubmit}>
            <InputText
                placeholder="Search a task"
                name="name"
                id="name"
                type="text"
                size="large"
                label="Name"
                maxLength={120}
                showCount
                onChange={handleInputChange}
                prefix={<FaSearch />}
            />
            <InputSelect
                name="priority"
                label="Priority"
                id="priority"
                onChange={(value) => handleChange(value, "priority")}
                options={[
                    { label: "All", value: "all" },
                    { label: "High", value: "HIGH" },
                    { label: "Medium", value: "MEDIUM" },
                    { label: "Low", value: "LOW" },
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
                    { label: "All", value: "all" },
                    { label: "Done", value: "true" },
                    { label: "Undone", value: "false" },
                ]}
                defaultValue="all"
                size="large"
            />
            <div className="w-full flex justify-center">
                <BaseButton
                    htmlType="submit"
                    text="Search"
                    size="large"
                    icon={<FaSearch />}
                    className="w-[300px] bg-blue-400 text-black hover:!bg-blue-500 
          hover:!border-blue-500 hover:!text-black border border-black"
                />
            </div>
        </form>
    )
}

export default FilterForm
