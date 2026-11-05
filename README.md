# Appium MCP 实战：让 AI 在真机上跑测试

用自然语言驱动 AI 在安卓真机上完成探索、定位、点击、断言，最后把操作固化成可回归的 TestNG 代码。零 API key、零视觉模型、零额外服务。

> **状态：草稿（DRAFT）** — 正文内容已按实测数据写完，图片素材仍在补拍中。以下"待补"项完成后再转正：
> - `docs/assets/06-trust.png`（连接器管理页 Trust 截图）
> - `docs/assets/01~05` 五张关键帧的重新拍摄（现有版本可看，但排版与清晰度待优化）

## 目录结构

```
docs/
  tutorial-draft-v0.md      正文草稿（约 440 行，七节 + FAQ）
  tutorial-outline.md       写作大纲：素材采集清单 + GEO 检查表
  assets/                   真机截图素材
demo-testng/                正文第 4 节产出的测试代码，可编译可运行
  pom.xml
  testng.xml
  src/test/java/tests/BluetoothToggleTest.java
```

## 正文目录

1. **为什么是 Appium MCP** — 传统移动端自动化的三高，MCP 改变了什么
2. **15 分钟环境准备** — MCP 配置、两个必踩的坑（ANDROID_HOME、Node v22）、Trust 这一步
3. **第一条指令：打开设置关蓝牙** — 13 次工具调用的完整链路、三个真实坑、110 秒实测
4. **从会话到资产：生成可回归的测试代码** — `appium_generate_tests` 实为"SOP 注入器"、生成代码零改动跑通、两套 driver 抢装 APK、国产 ROM 四道门槛（含 `-g` 参数这个真元凶）
5. **分阶段方法论：不裸调工具** — 裸调 vs SOP 对照实验（37 次 / 3'52" vs 32 次 / 3'04"）、三条立论、可抄给 AI 的四阶段 SOP
6. **进阶一瞥** — 视觉定位、OCR/YOLO 自建服务、iOS 真机
7. **这不只是玩具** — 个人与团队两种用法

## 复现

```bash
# 1. 环境：Node ≥ v22、JDK ≥ 8、Android SDK（adb 可用）、开了 USB 调试的真机
adb devices

# 2. 把 appium-mcp 写进你的 AI 助手的 MCP 配置（见正文 §2），重启并 Trust

# 3. 跑测试代码（需另起 Appium server，并显式给它 ANDROID_HOME）
cd demo-testng
ANDROID_HOME=/path/to/android-sdk appium --port 4723 --address 127.0.0.1 &
mvn test
```

## 环境备注

- 实测机型：OPPO Reno7 / ColorOS（Android），因此文中"国产 ROM 四道门槛"一节针对 ColorOS 的安装器行为，其他 ROM 表现可能不同。
- 生成代码需要 `selenium-bom 4.33.0` 锁定版本（原因见正文 §4 手改 1）。
- 同一台设备建议只用一条 driver 链（MCP 内嵌 或 全局 Appium server），否则两边会互相重装辅助 APK。

## 许可

正文与代码采用 [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/)（署名即可转载）。
