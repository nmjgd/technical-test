# 技術テスト成果物

## プログラム作成環境

### 利用アーキテクチャ

- 言語
  - Java
- フレームワーク・ライブラリ・ソフトウェア
  - SpringBoot
  - Jooq
  - Lombok
  - WireMock
  - Docker

### Docker を利用した環境

- JDK
- MySQL
- WireMock
- phpMyAdmin（DB データ確認用）

## フォルダ構成

```
.
├── client
│   └── technical-test     // 技術テストプログラムフォルダ（UnitTest含む）
├── docker
│  ├── db
│  │   └── initdb.d        // DB初期化スクリプトフォルダ
│  └── mockapi             // モックAPI設定フォルダ
│
└── docker-compose.yml
```
