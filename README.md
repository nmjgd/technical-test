# 技術テスト成果物

## 作成プログラムについて

- 言語はJavaを選択させて頂きました。
- Webアプリでなく、Javaアプリケーションとして作成しております。

## フォルダ構成

```
.
├── client
│   └── technical-test     // 技術テストプログラムフォルダ（UnitTest含む）
├── docker
│  ├── db
│  │   └── initdb.d        // DB初期化スクリプトフォルダ
│  └── mockapi             // Mock API設定フォルダ
│
└── docker-compose.yml
```

## プログラム作成環境

### 利用アーキテクチャ

- SpringBoot
- JOOQ
- Lombok
- WireMock
- Docker

### Docker を利用した環境

- JDK
- MySQL
- WireMock
- phpMyAdmin（DB データ確認用）

## Mock API 仕様

- リクエスト成功
  - Request Body が以下の JSON 仕様
  - image_path の suffix が`analyzable.jpg`

```JSON
{
  "image_path": "/dir/analyzable.jpg"
}
```

- 内部サーバーエラー
  - Request Body が以下の JSON 仕様
  - image_path が 500

```JSON
{
  "image_path": "500"
}
```

- リクエスト失敗
  - 上記のいずれにも該当しない場合
