import { Button } from "antd"
import { SizeType } from "antd/es/config-provider/SizeContext"

interface ButtonProps {
  htmlType: "button" | "submit" | "reset" | undefined
  text: string
  size?: SizeType
  className?: string
  onClick?: () => void | { payload: any; type: "createModal/controlCreate"; }
}

const BaseButton = ({ text, htmlType, size, className, onClick }: ButtonProps) => {
  return (
    <Button
      htmlType={htmlType}
      size={size}
      className={className}
      onClick={onClick}
    >
      {text}
    </Button>
  )
}

export default BaseButton