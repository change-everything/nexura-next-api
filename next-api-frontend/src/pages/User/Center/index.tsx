import { PageContainer } from '@ant-design/pro-components';

import { getUserVoByIdUsingGet, resetKeyUsingPost } from '@/services/next-api/userController';
import { useModel } from '@@/exports';
import { CopyOutlined } from '@ant-design/icons';
import { Avatar, Button, Card, Descriptions, DescriptionsProps, Divider, message } from 'antd';
import 'monaco-editor/min/vs/editor/editor.main.css';
import React, { useEffect, useState } from 'react';
import { CopyToClipboard } from 'react-copy-to-clipboard';

const Index: React.FC = () => {
  const [data, setData] = useState<API.UserVO>();
  const [userLoading, setUserLoading] = useState<boolean>(false);
  const { initialState } = useModel('@@initialState');

  const loadData = async () => {
    setUserLoading(true);

    const userRes = await getUserVoByIdUsingGet({
      id: initialState?.currentUser?.id,
    });
    if (userRes.data) {
      setData(userRes.data);
    }

    setUserLoading(false);
  };

  useEffect(() => {
    loadData();
  }, []);

  const items: DescriptionsProps['items'] = [
    {
      key: '1',
      label: '用户名',
      children: `${data?.userName}`,
    },
    {
      key: '2',
      label: '人生格言',
      children: `${data?.userProfile ?? '这个人很懒，什么都没写'}`,
    },
    {
      key: '3',
      label: '角色',
      children: `${data?.userRole}`,
    },
  ];

  const appItems: DescriptionsProps['items'] = [
    {
      key: '1',
      label: 'API Key',
      children: `${data?.accessKey}`,
    },
    {
      key: '2',
      label: 'Secret Key',
      children: `${data?.secretKey}`,
    },
  ];

  return (
    <PageContainer title="用户中心" loading={userLoading}>
      <Card>
        <Descriptions title="用户信息" items={items} extra={<Avatar src={data?.userAvatar} />} />
      </Card>
      <Divider />
      <Card
        title="API密钥"
        extra={
          <Button
            type="primary"
            onClick={async () => {
              const res = await resetKeyUsingPost();
              if (res.code === 0) {
                message.success('生成成功');
                loadData();
              }
            }}
          >
            重新生成
          </Button>
        }
      >
        <span style={{ fontSize: 'larger', fontWeight: 'bolder' }}>Access Key: </span>
        <span style={{ fontWeight: 'normal' }}>{data?.accessKey}</span>
        <CopyToClipboard
          text={data?.accessKey}
          onCopy={(_, result) => {
            if (result) {
              message.success('复制成功');
            } else {
              message.error('复制失败，请稍后再试');
            }
          }}
        >
          <Button style={{ color: '#722ED1' }} type="link" icon={<CopyOutlined />} />
        </CopyToClipboard>
        <br />
        <span style={{ fontSize: 'larger', fontWeight: 'bolder' }}>Secret Key: </span>
        <span style={{ fontWeight: 'normal' }}>{data?.secretKey}</span>
        <CopyToClipboard
          text={data?.secretKey}
          onCopy={(_, result) => {
            if (result) {
              message.success('复制成功');
            } else {
              message.error('复制失败，请稍后再试');
            }
          }}
        >
          <Button style={{ color: '#722ED1' }} type="link" icon={<CopyOutlined />} />
        </CopyToClipboard>
      </Card>
    </PageContainer>
  );
};

export default Index;
