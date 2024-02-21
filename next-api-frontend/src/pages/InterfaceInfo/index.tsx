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
  Space,
  Spin,
  Table,
  Tabs,
  Tag,
  message,
} from 'antd';
import moment from 'moment';
import 'monaco-editor/min/vs/editor/editor.main.css';
import React, { useEffect, useState } from 'react';

const Index: React.FC = () => {
  const [data, setData] = useState<API.InterfaceInfo>();
  const [invokeRes, setInvokeRes] = useState<any>();
  const [invokeLoading, setInvokeLoading] = useState<boolean>(false);
  const [exampleRequestParams, setExampleRequestParams] = useState('');
  const params = useParams();

  const [requestDataSource, setRequestDataSource] = useState<API.InterfaceParams[]>([]);
  const [responseDataSource, setResponseDataSource] = useState<API.InterfaceResponse[]>([]);
  const [exampleResponse, setExampleResponse] = useState('');

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
      children: <Tag color="gold">{data?.method}</Tag>,
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

  const finalResult = {
    data: '',
    code: 0,
    msg: 'success',
  };

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
      setRequestDataSource(res.data?.requestParams ?? []);
      setResponseDataSource(res.data?.responseParams ?? []);
      const requestData: API.InterfaceParams[] = res.data?.requestParams ?? [];
      // 构建目标数据结构
      const transformedData: { [key: string]: string } = requestData.reduce((acc, param) => {
        acc[param.paramName] = param.exampleValue;
        return acc;
      }, {});
      setExampleRequestParams(JSON.stringify(transformedData ?? '{}', null, 2));
      finalResult.data = transformedData;
      setExampleResponse(JSON.stringify(finalResult, null, 2));
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
        userRequestParams: exampleRequestParams,
      });
      finalResult.data = res.data;
      setInvokeRes(JSON.stringify(finalResult, null, 2));
    } catch (e: any) {
      finalResult.data = '';
      finalResult.data = '50000';
      finalResult.msg = '请求失败, ' + e.message;
      setInvokeRes(JSON.stringify(finalResult, null, 2));
      message.error('请求失败，' + e.message);
    }
    setInvokeLoading(false);
  };

  const requestColumns = [
    {
      title: '参数名称',
      dataIndex: 'paramName',
      key: 'paramName',
    },
    {
      title: '参数类型',
      dataIndex: 'paramType',
      key: 'paramType',
    },
    {
      title: '示例值',
      dataIndex: 'exampleValue',
      key: 'exampleValue',
    },
    {
      title: '是否必填',
      dataIndex: 'isMust',
      key: 'isMust',
      render: (_, record) => [record.isMust === 0 ? '是' : '否'],
    },
    {
      title: '参数描述',
      dataIndex: 'description',
      key: 'description',
    },
  ];

  const responseColumns = [
    {
      title: '参数名称',
      dataIndex: 'responseName',
      key: 'responseName',
    },
    {
      title: '参数类型',
      dataIndex: 'responseType',
      key: 'responseType',
    },
    {
      title: '示例值',
      dataIndex: 'exampleValue',
      key: 'exampleValue',
    },
    {
      title: '参数描述',
      dataIndex: 'description',
      key: 'description',
    },
  ];
  const codeColumns = [
    {
      title: '返回码',
      dataIndex: 'codeName',
      key: 'codeName',
    },
    {
      title: '返回码描述',
      dataIndex: 'description',
      key: 'description',
    },
  ];

  const codeDataSource = [
    {
      key: '1',
      codeName: '0',
      description: '成功',
    },
    {
      key: '2',
      codeName: '40400',
      description: '请求路径错误',
    },
    {
      key: '3',
      codeName: '50000',
      description: '系统错误',
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
          <Tabs.TabPane tab="请求示例" key="1">
            <h3>请求参数</h3>
            <Table dataSource={requestDataSource} columns={requestColumns} pagination={false} />
            <Divider />
            <h3>响应参数</h3>
            <Table dataSource={responseDataSource} columns={responseColumns} pagination={false} />
            <Divider />
            <h3>
              <Space>
                响应示例<Tag color="purple">正常</Tag>
              </Space>
            </h3>
            <CodeEdit value={exampleResponse} onChange={() => {}} />
            <Divider />
            <h3>响应码</h3>
            <Table dataSource={codeDataSource} columns={codeColumns} pagination={false} />
          </Tabs.TabPane>
          <Tabs.TabPane tab="在线调试" key="2">
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
          </Tabs.TabPane>
        </Tabs>
      </Card>
    </PageContainer>
  );
};

export default Index;
