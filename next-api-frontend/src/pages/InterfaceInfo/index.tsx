import CodeEdit from '@/components/CodeEdit';
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
  Table,
  Tabs,
  message,
} from 'antd';
import TabPane from 'antd/es/tabs/TabPane';
import moment from 'moment';
import 'monaco-editor/min/vs/editor/editor.main.css';
import React, { useEffect, useState } from 'react';

const Index: React.FC = () => {
  const [data, setData] = useState<API.InterfaceInfo>();
  const [invokeRes, setInvokeRes] = useState<any>();
  const [invokeLoading, setInvokeLoading] = useState<boolean>(false);
  const [userRequestParams, setUserRequestParams] = useState('');
  const [exampleRequestParams, setExampleRequestParams] = useState('');
  const params = useParams();

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
      children: moment(`${data?.createTime}`).format('YYYY-MM-DD HH:mm:ss'),
      span: 2,
    },
    {
      key: '8',
      label: '更新时间',
      children: moment(`${data?.updateTime}`).format('YYYY-MM-DD HH:mm:ss'),
      span: 2,
    },
  ];

  const onChange = (key: string) => {
    console.log(key);
  };

  const loadData = async () => {
    if (!params.id) {
      message.error('参数不存在');
      return;
    }
    try {
      const res = await getInterfaceInfoByIdUsingGet({
        id: params.id as any,
      });
      setData(res.data);
      setUserRequestParams(res.data?.userRequestParams ?? '');
      setExampleRequestParams(res.data?.exampleRequestParams ?? '');
    } catch (e: any) {
      message.error('请求失败，' + e.message);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const onFinish = async () => {
    setInvokeLoading(true);
    if (!params.id) {
      message.error('参数不存在');
      return;
    }
    try {
      const res = await invokeInterfaceUsingPost({
        url: data?.url,
        method: data?.method,
        id: params.id as any,
        name: data?.name,
        userRequestParams: userRequestParams,
      });
      setInvokeRes(res.data);
    } catch (e: any) {
      message.error('请求失败，' + e.message);
    }
    setInvokeLoading(false);
  };

  const dataSource = [
    {
      key: '1',
      name: '胡彦斌',
      age: 32,
      address: '西湖区湖底公园1号',
    },
    {
      key: '2',
      name: '胡彦祖',
      age: 42,
      address: '西湖区湖底公园1号',
    },
  ];

  const columns = [
    {
      title: '参数名称',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '参数类型',
      dataIndex: 'age',
      key: 'age',
    },
    {
      title: '是否必填',
      dataIndex: 'address',
      key: 'address',
    },
    {
      title: '示例值',
      dataIndex: 'address',
      key: 'address',
    },
    {
      title: '参数描述',
      dataIndex: 'address',
      key: 'address',
    },
  ];
  return (
    <PageContainer title="查看接口文档">
      <Card>
        <Descriptions title={data?.name} bordered items={items} />
      </Card>
      <Divider />
      <Card>
        <Tabs onChange={onChange} type="card" size="large">
          <TabPane tab="请求示例" key="1">
            <Table dataSource={dataSource} columns={columns} pagination={false} />
          </TabPane>
          <TabPane tab="在线调试" key="2">
            <Card title="请求参数">
              <Form name="basic" layout="vertical" onFinish={onFinish}>
                <Form.Item name="userRequestParams">
                  <Input.TextArea defaultValue={exampleRequestParams} style={{ display: 'none' }} />
                  <CodeEdit
                    value={exampleRequestParams || ''}
                    onChange={(evn) => setUserRequestParams(evn.target.value)}
                  />
                </Form.Item>

                <Form.Item wrapperCol={{ span: 16 }}>
                  <Button type="primary" htmlType="submit">
                    调用
                  </Button>
                </Form.Item>
              </Form>
            </Card>
            <Card title="响应结果">
              <Spin spinning={invokeLoading} />
              <CodeEdit value={invokeRes || ''} onChange={() => {}} />
            </Card>
          </TabPane>
        </Tabs>
      </Card>
    </PageContainer>
  );
};

export default Index;
