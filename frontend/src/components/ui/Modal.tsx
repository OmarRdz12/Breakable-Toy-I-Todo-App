import { Modal } from "antd"
import { ReactNode } from "react"

interface BaseModal {
    openModal: boolean
    closeModal: () => void
    children: ReactNode
    text?: string
    title: string
    onSubmit: () => void
}

const BaseModal = ({ title, openModal, closeModal, children, text = "OK", onSubmit }: BaseModal) => {
    return (
        <Modal
            title={title}
            centered
            open={openModal}
            onOk={onSubmit}
            okText={text}
            onCancel={closeModal}
            width={{
                xs: '90%',
                sm: '80%',
                md: '70%',
                lg: '60%',
                xl: '50%',
                xxl: '40%',
            }}
        >
            {children}
        </Modal>
    )
}

export default BaseModal