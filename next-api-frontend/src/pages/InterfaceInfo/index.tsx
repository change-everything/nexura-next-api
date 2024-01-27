import {
  getInterfaceInfoByIdUsingGet,
  invokeInterfaceUsingPost,
} from '@/services/next-api/interfaceInfoController';
import { useParams } from '@@/exports';
import { PageContainer } from '@ant-design/pro-components';
import {
  Badge,
  Button,
  Card,
  Descriptions,
  DescriptionsProps,
  Divider,
  Form,
  Input,
  Spin,
  message,
} from 'antd';
import React, { useEffect, useState } from 'react';

const Index: React.FC = () => {
  const [data, setData] = useState<API.InterfaceInfo>();
  const [invokeRes, setInvokeRes] = useState<any>();
  const [invokeLoading, setInvokeLoading] = useState<boolean>(false);
  const params = useParams();

  const loadData = async () => {
    if (!params.id) {
      message.error('参数不存在');
      return;
    }
    try {
      const res = await getInterfaceInfoByIdUsingGet({
        id: params.id,
      });
      setData(res.data);
    } catch (e: any) {
      message.error('请求失败，' + e.message);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const onFinish = async (values: any) => {
    setInvokeLoading(true);
    if (!params.id) {
      message.error('参数不存在');
      return;
    }
    try {
      const res = await invokeInterfaceUsingPost({
        id: params.id,
        ...values,
      });
      setInvokeRes(res.data);
    } catch (e: any) {
      message.error('请求失败，' + e.message);
    }
    setInvokeLoading(false);
  };

  const items: DescriptionsProps['items'] = [
    {
      key: '6',
      label: '接口状态',
      children:
        data?.status === 1 ? (
          <Badge status="processing" text="运行中" />
        ) : (
          <Badge status="default" text="关闭" />
        ),
    },
    {
      key: '2',
      label: '请求地址',
      children: `${data?.url}`,
      span: 2,
    },
    {
      key: '3',
      label: '请求方法',
      children: `${data?.method}`,
    },
    {
      key: '4',
      label: '请求头',
      children: `${data?.requestHeader}`,
    },
    {
      key: '5',
      label: '响应头',
      children: `${data?.responseHeader}`,
    },
    {
      key: '1',
      label: '描述',
      children: `${data?.description}`,
      span: 3,
    },
    {
      key: '7',
      label: '创建时间',
      children: `${data?.createTime}`,
      span: 2,
    },
    {
      key: '8',
      label: '更新时间',
      children: `${data?.updateTime}`,
      span: 2,
    },
  ];

  return (
    <PageContainer title="查看接口文档">
      <Card>
        <Descriptions title={data?.name} bordered items={items} />
      </Card>
      <Divider />
      <Card title="在线测试">
        <Form name="basic" layout="vertical" onFinish={onFinish}>
          <Form.Item label="请求参数" name="userRequestParams">
            <Input.TextArea value={data?.requestParams} />
          </Form.Item>

          <Form.Item wrapperCol={{ span: 16 }}>
            <Button type="primary" htmlType="submit">
              调用
            </Button>
          </Form.Item>
        </Form>
      </Card>
      <Divider />
      <Card title="返回结果">
        <Spin spinning={invokeLoading} />
        {invokeRes}
      </Card>
    </PageContainer>
  );
};

export default Index;
