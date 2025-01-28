import { Button, Tooltip } from "antd"
import { ButtonShape } from "antd/es/button"
import { SizeType } from "antd/es/config-provider/SizeContext"
import { ReactNode } from "react"

interface ButtonProps {
  htmlType: "button" | "submit" | "reset" | undefined
  text: string
  size?: SizeType
  className?: string
  onClick?: () => void | { payload: any; type: "createModal/controlCreate"; } | { payload: any; type: "updateModal/controlUpdate"; } | Promise<void>
  toolTip?: boolean
  shape?: ButtonShape
  icon?: ReactNode
  disabled?: boolean
}

const BaseButton = ({ text, htmlType, size, className, onClick, icon, shape, toolTip = false, disabled = false }: ButtonProps) => {
  return (
    <>
      {
        toolTip ?
          <Tooltip title={text}>
            <Button
              shape={shape}
              icon={icon}
              onClick={onClick}
              className={className}
              disabled={disabled}
            />
          </Tooltip>
          :
          <Button
            htmlType={htmlType}
            size={size}
            className={className}
            onClick={onClick}
            disabled={disabled}
          >
            {text}
          </Button >
      }
    </>
  )
}

export default BaseButton