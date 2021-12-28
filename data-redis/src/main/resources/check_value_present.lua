-- 检查参数值是否在指定set里存在,如果存在则为0,不存在则为1.并且把不存在的参数值存入到该set中
-- KEYS[1] 为 Set结构名称
-- ARGV 为所有参数
--return list<int>
local result = {}
for i = 1, #ARGV do
    local data = ARGV[i]
    --如果值不存在返回1,如果存在返回0
    result[i] = redis.call('SADD', KEYS[1], data)
end
return result;