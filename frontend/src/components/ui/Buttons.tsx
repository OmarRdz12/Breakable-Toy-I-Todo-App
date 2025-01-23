import { Button } from "antd"
import { SizeType } from "antd/es/config-provider/SizeContext"

interface ButtonProps {
    htmlType: "button" | "submit" | "reset" | undefined
    text: string
    size?: SizeType
    className?: string
}

const BaseButton = ({text, htmlType, size, className}: ButtonProps) => {
  return (
    <Button 
     htmlType={htmlType} 
     size={size}
     className={className}
    >
        {text}
    </Button>
  )
}

export default BaseButton