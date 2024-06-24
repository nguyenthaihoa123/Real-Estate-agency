# Real Estate Website

## Giới thiệu đề tài 
Theo yêu cầu đề ra, nhóm tiến hành xấy dựng hệ thống hỗ trợ quản lý bất động sản dựa trên nền tảng Java Spring Boot, giúp kết nối người mua, người bán, chủ nhà và người thuê nhà một cách hiệu quả. Thông qua các chức năng cần thiết như tìm kiếm bất động sản, giúp khách hàng linh hoạt trong việc tìm kiếm sao cho phù hợp với nhu cầu của họ. Hệ thống giúp khách hàng liên hệ với mua giới để được tư vấn và hỗ trợ. Đặc biệt, hệ thống cung cấp cho khách hàng thông tin chi tiết về bất động sản, bao gồm hình ảnh, mô tả, giá cả và vị trí. Ngoài ra, việc hoàn thành giao dịch mua bán hoặc cho thuê bất động sản cũng hoàn thành nhanh chóng và tiện lợi.
Nhìn chung, hệ thống mạng lại nhiều lợi ích cho doanh nghiệp và khách hàng. Hệ thống giúp doanh nghiệp hoạt động hiệu quả hơn, tăng khả năng cạnh tranh và mang lại trải nghiệm tốt cho khách hàng.
## Mục tiêu
-	Hệ thống hỗ trợ kinh doanh bất động sản được tạo ra nhằm phục vụ nhu cầu tìm kiếm bất động sản phù hợp với sở thích, yêu cầu và khả năng tài chính của người dùng.
-	Hệ thống hỗ trợ trong việc đặt lịch xem nhà với mua giới giúp khách hàng tiết kiệm thời gian và công sức trong quá trình tìm kiếm.
-	Hệ thống cung cấp đầy đủ các thông tin chi tiết về bất động sản, hỗ trợ thực hiện các giao dịch mua, bán và cho thuê nhanh chóng, an toàn và hiệu quả.
## Đặc tả chức năng
•	Client: 
-	Đăng ký/ đăng nhập: Người dùng có thể đăng ký tài khoản mới trên hệ thống hoặc đăng nhập bằng tài khoản google, …
-	Quản lí thông tin cá nhân: Thay đổi mật khẩu, chỉnh sửa, cập nhật thông tin cá nhân, …
-	Tìm kiếm bất động sản: Tìm kiếm bất động sản theo các tiêu chí như vị trí, giá cả, loại hình, diện tích, …
-	Xem thông tin chi tiết bất động sản: Xem hình ảnh, mô tả, tiện ích, giá cả...
-	Liên hệ với môi giới: Gửi yêu cầu liên hệ, đặt lịch hẹn xem nhà.
-	Lưu BĐS yêu thích: Lưu các tin BĐS yêu thích để dễ dàng tham khảo khi cần.
-	So sánh bất động sản: So sánh các tin BĐS theo nhiều tiêu chí.
-	Đánh giá bất động sản: Để lại đánh giá sau khi hoàn thành giao dịch.

•	Agent: 
-	Quản lý danh sách bất động sản: Thêm, sửa, xóa, cập nhật thông tin về BĐS.
-	Đăng tin bán/ cho thuê: Đăng tin bán/ cho thuê bất động sản lên hệ thống để thông tin đến khách hàng.
-	Quản lý các hợp đồng cho thuê/ bán: Tạo, chỉnh sửa các văn bản hợp đồng.
-	Mua gói đăng tin: Agent mua gói đăng tin từ hệ thống để tiến hành đăng bài.
-	Tạo hợp đồng: Tiến hành tạo hợp đồng khi khách hàng mua hoặc thuê BĐS.

•	Admin:
-	Quản lý bất động sản: Admin thực hiện phe duyệt các bài đăng bất động sản của Agent lên hệ thống. Đồng thời, có thể xóa bất động sản.
-	Quản lý agent: Admin duyệt tài khoản, xóa tài khoản, ...của Agent khi cần thiết.
-	Quản lý client: Admin có thể xóa, đổi mật khẩu của Client, …
-	Thống kê doanh thu: Tiến hành thống kê doanh thu dưới dạng biểu đồ, …

•	Email Service: Thực hiện chức năng gửi thông báo đên Client và Agent khi cần thiết.  

•	VnPay: Làm nhiệm vụ trung gian thanh toán giữa Agent với hệ thống.
## Sơ đồ USECASE
![image](https://github.com/nguyenthaihoa123/Real-Estate-agency/assets/94378718/39055b37-f37a-4e0c-aa0f-de4b2b4f4670)
## Sơ đồ ERD mức conceptual
![image](https://github.com/nguyenthaihoa123/Real-Estate-agency/assets/94378718/f77048fb-2caf-494c-9ab3-c3e9494f86a1)
## Giao diện
### Agent
![image](https://github.com/nguyenthaihoa123/Real-Estate-agency/assets/94378718/fc40ffe7-8adb-4bab-bcc8-d368c3fd6c5a)
Trang cá nhân
![image](https://github.com/nguyenthaihoa123/Real-Estate-agency/assets/94378718/83b7e28b-0d87-454d-bedf-b322de59e317)
Thêm tài sản
![image](https://github.com/nguyenthaihoa123/Real-Estate-agency/assets/94378718/a9fd216c-411e-4974-ad65-373be518d7ce)
Danh sách tài sản
![image](https://github.com/nguyenthaihoa123/Real-Estate-agency/assets/94378718/2be3bb9f-ed26-4cc3-8686-ab82c4871424)
Quản lý hợp đồng
![image](https://github.com/nguyenthaihoa123/Real-Estate-agency/assets/94378718/5c86ad37-4953-410c-8c30-2f579e2c3e48)
Lịch hẹn
![image](https://github.com/nguyenthaihoa123/Real-Estate-agency/assets/94378718/7c83682a-525b-40cb-bc64-0766146f7805)
Mua gói đăng tin
### Client
![image](https://github.com/nguyenthaihoa123/Real-Estate-agency/assets/94378718/c327c4f3-6c74-4f75-b6ac-994a24a2aaf6)
So sánh sản phẩm
### Admin
![image](https://github.com/nguyenthaihoa123/Real-Estate-agency/assets/94378718/7600f897-e726-4c2b-8409-5ea40a068c87)
Quản lý thông tin
![image](https://github.com/nguyenthaihoa123/Real-Estate-agency/assets/94378718/45dd9041-de89-414c-affd-856debcd7766)
Thống kê doanh thu từ trang web












