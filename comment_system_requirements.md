# 校园二手交易平台 - 评论管理系统需求文档

## 1. 项目背景与目标
基于校园二手交易平台（小程序端 + PC管理端 + 后端服务）架构，设计一个轻量级、 MVP（最小可行性产品）级别的评论交互系统。
- **核心交互**：买家对商品提问/评论 -> 卖家回复。
- **管理目标**：PC管理端负责内容监控与违规处理，确保平台言论安全。

---

## 2. 数据库设计需求 (Database Schema)

采用单表结构存储“提问-回复”对，简化查询逻辑。

### 2.1 新增表结构：`store_good_comment`

| 字段名 | 类型 | 长度 | 是否为空 | 默认值 | 描述 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `comment_id` | INT | 11 | NO | AUTO_INCREMENT | **主键**，评论ID |
| `user_id` | INT | 11 | NO | - | **外键**，买家ID (关联 store_user) |
| `good_id` | INT | 11 | NO | - | **外键**，商品ID (关联 store_good) |
| `content` | VARCHAR | 512 | NO | - | 买家评论/提问内容 |
| `reply_content` | VARCHAR | 512 | YES | NULL | 卖家回复内容 (为空表示未回复) |
| `reply_time` | DATETIME | - | YES | NULL | 卖家回复时间 |
| `create_time` | DATETIME | - | YES | CURRENT_TIMESTAMP | 评论创建时间 |
| `is_show` | TINYINT | 1 | YES | 1 | 状态 (1:显示, 0:隐藏/软删除) |

### 2.2 SQL 建表语句
```sql
CREATE TABLE `store_good_comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `user_id` int(11) NOT NULL COMMENT '买家ID',
  `good_id` int(11) NOT NULL COMMENT '商品ID',
  `content` varchar(512) NOT NULL COMMENT '评论内容',
  `reply_content` varchar(512) DEFAULT NULL COMMENT '卖家回复内容',
  `reply_time` datetime DEFAULT NULL COMMENT '回复时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_show` tinyint(1) DEFAULT '1' COMMENT '是否显示(0:隐藏 1:显示)',
  PRIMARY KEY (`comment_id`),
  KEY `idx_good_id` (`good_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品评论表';
```

---

## 3. PC管理端前端需求 (Frontend Requirements)

### 3.1 页面功能概览
*   **页面标题**：评论管理 (Comment Management)
*   **主要功能**：
    1.  查看所有商品下的买卖双方对话。
    2.  删除违规的买家评论。
    3.  屏蔽违规的卖家回复。

### 3.2 界面交互设计

#### A. 顶部筛选栏 (Filter Area)
*   **[输入框] 商品搜索**：支持输入商品名称或商品ID进行模糊搜索。
*   **[输入框] 用户搜索**：支持输入买家昵称搜索。
*   **[下拉框] 回复状态**：
    *   全部 (Default)
    *   已回复
    *   未回复
*   **[按钮]**：查询、重置

#### B. 数据列表 (Data Table)
表格需展示以下列：

| 列标题 | 数据绑定 | 显示逻辑/UI建议 |
| :--- | :--- | :--- |
| **ID** | `comment_id` | 居中显示 |
| **商品信息** | `good_name` + `image` | 显示缩略图(50px) + 商品名称链接（点击可预览商品详情） |
| **买家信息** | `nickname` | 显示买家昵称 |
| **提问内容** | `content` | 🔴 **重点展示**。若文字超过50字，截断显示“...”，鼠标悬停显示完整内容。 |
| **提问时间** | `create_time` | 格式：`YYYY-MM-DD HH:mm` |
| **卖家回复** | `reply_content` | 若有回复，显示回复内容；若无，显示灰色“暂无回复”。 |
| **回复时间** | `reply_time` | 若无回复则留空 |
| **操作** | - | **[删除评论]** (红色按钮) <br> **[屏蔽回复]** (仅当有回复时显示) |

#### C. 操作交互 (Action Logic)
1.  **删除评论 (Delete Comment)**
    *   **触发**：点击列表中的“删除评论”按钮。
    *   **确认**：弹出Modal框提示“删除后，该评论及其对应的卖家回复将对用户不可见，是否确认？”
    *   **执行**：调用后端接口软删除该条记录。
2.  **屏蔽回复 (Delete Reply)**
    *   **触发**：点击列表中的“屏蔽回复”按钮。
    *   **确认**：弹出Modal框提示“确认屏蔽该条回复内容吗？买家提问将保留。”
    *   **执行**：调用后端接口清空 `reply_content` 字段。

---

## 4. 后端接口需求 (API Requirements)

前端开发需调用以下后端接口（建议路径）：

### 4.1 分页获取评论列表
*   **Endpoint**: `GET /admin/comment/list`
*   **Request Params**:
    *   `page`: 当前页码
    *   `limit`: 每页数量
    *   `goodName`: (可选) 商品名称关键词
    *   `nickname`: (可选) 买家昵称关键词
    *   `isReplied`: (可选) boolean, 是否已回复
*   **Response Data**:
    ```json
    {
      "code": 200,
      "data": {
        "total": 100,
        "rows": [
          {
            "commentId": 1,
            "content": "还能便宜吗？",
            "replyContent": "不刀了同学",
            "createTime": "2023-10-27 10:00:00",
            "goodName": "考研英语书",
            "goodImage": "http://...",
            "nickname": "张三"
          }
        ]
      }
    }
    ```

### 4.2 删除/隐藏评论
*   **Endpoint**: `POST /admin/comment/remove`
*   **Request Body**: `{ "ids": [1, 2] }` (支持批量)
*   **Logic**: 将数据库中对应记录的 `is_show` 字段置为 0。

### 4.3 屏蔽回复
*   **Endpoint**: `POST /admin/comment/removeReply`
*   **Request Body**: `{ "commentId": 1 }`
*   **Logic**: 将数据库中对应记录的 `reply_content` 字段置为 NULL 或空字符串。
