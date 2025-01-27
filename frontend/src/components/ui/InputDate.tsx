import { DatePicker } from "antd";
import { SizeType } from "antd/es/config-provider/SizeContext"
import { Dayjs } from 'dayjs'

interface InputDateProps {
    id: string
    label: string
    required?: boolean
    onChange: (date: any, dateString: string | string[]) => void
    size?: SizeType
    name: string
    value?: string | number | Dayjs | Date | null | undefined
}

const InputDate = ({ id, label, required = false, onChange, name, size, value }: InputDateProps) => {


    return (
        <div className="flex gap-2 flex-col">
            <label htmlFor={id}>{label}{required && <span className="text-red-500"> *</span>}</label>
            <DatePicker
                name={name}
                onChange={onChange}
                required={required}
                size={size}
                id={id}
                value={value}
            />
        </div>
    )
}

export default InputDate