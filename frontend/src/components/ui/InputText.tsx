import { ConfigProvider, Input } from "antd"
import { BaseInputProps } from "./types"

const InputText = ({
  name,
  placeholder,
  id,
  value,
  className,
  onChange,
  size,
  prefix,
  type = "text",
  label,
  required = false,
  maxLength,
  showCount = false,
  variant
}: BaseInputProps) => {
  return (
    <ConfigProvider
      theme={{
        token: {
          colorBorder: "#000000",
        },
        components: {
          Input: {
            activeBorderColor: "#000000",
            hoverBorderColor: "#000000",
          }
        }
      }}
    >
      <div className="flex gap-2 flex-col">
        <label htmlFor={id}>{label}{required && <span className="text-red-500"> *</span>}</label>
        <Input
          name={name}
          placeholder={placeholder}
          id={id}
          className={className}
          value={value}
          onChange={onChange}
          size={size}
          prefix={prefix}
          type={type}
          maxLength={maxLength}
          showCount={showCount}
          variant={variant}
        />
      </div>
    </ConfigProvider>
  )
}

export default InputText