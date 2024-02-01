import { ProColumns, ProTable } from '@ant-design/pro-components';
import '@umijs/max';
import { Form, Modal } from 'antd';
import React from 'react';

export type Props = {
  columns: ProColumns<API.InterfaceInfo>[];
  onCancel: () => void;
  onSubmit: (values: API.InterfaceInfo) => Promise<void>;
  visible: boolean;
};
const CreateForm: React.FC<Props> = (props) => {
  const { visible, columns, onCancel, onSubmit } = props;
  const [form] = Form.useForm();
  return (
    <Modal open={visible} onCancel={() => onCancel?.()} footer={null} width="70%">
      <ProTable
        type="form"
        columns={columns}
        onSubmit={async (value) => {
          onSubmit?.(value);
        }}
      />
    </Modal>
  );
};
export default CreateForm;
