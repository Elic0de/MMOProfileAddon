## 既存の確認画面の設定
さらに確認画面を表示させる場合は、yesのfunctionを`confirm-yes`に変更してください

profile-list.yml

```diff
# GUI display name
name: 'Character Deletion'

# Number of slots in your inventory. Must be
# between 9 and 54 and must be a multiple of 9.
slots: 27

items:
  yes:
+    function: 'confirm-yes'
-    function: 'yes'
    slots: [11]
    name: '&cYes, delete my character!'
    item: REDSTONE
  back:
    function: 'back'
    name: '&aNo!! :('
    slots: [15]
    item: EMERALD


```

## 追加画面の設定

additional-confirm-deletion.yml

```yaml
# GUI display name
name: '&0&lこのキャラクターを永久に削除しますか？'

# Number of slots in your inventory. Must be
# between 9 and 54 and must be a multiple of 9.
slots: 27

items:
  yes:
    function: 'yes'
    slots: [11]
    name: '&c&l永久に削除する!'
    item: RED_WOOL
  back:
    function: 'back'
    name: '&a&lキャラクター選択画面に戻る'
    slots: [15]
    item: LIME_WOOL
```
