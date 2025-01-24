import { Select } from "antd"
import { SelectInputProps } from "./types"

const InputSelect = ({id, label, required, onChange, name, options, defaultValue, size, value}: SelectInputProps) => {
  return (
    <div className="flex gap-2 flex-col">
        <label htmlFor={id}>{label}{required && <span className="text-red-500"> *</span>}</label>
        <Select
            title={name}
            id={id}
            onChange={onChange}
            options={options}
            defaultValue={defaultValue}
            size={size}
            value={value}
        /> 
    </div>
  )
}

export default InputSelect