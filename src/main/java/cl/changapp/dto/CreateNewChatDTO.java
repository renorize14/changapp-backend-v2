package cl.changapp.dto;

import java.time.LocalDateTime;

public class CreateNewChatDTO {
		
		private Long chatId;
        private Long senderId;
        private Long receiverId;
        private String content;
        private LocalDateTime timestamp;
        private Boolean read;
        
		public Long getSenderId() {
			return senderId;
		}
		public void setSenderId(Long senderId) {
			this.senderId = senderId;
		}
		public Long getReceiverId() {
			return receiverId;
		}
		public void setReceiverId(Long receiverId) {
			this.receiverId = receiverId;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public LocalDateTime getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}
		public Boolean getRead() {
			return read;
		}
		public void setRead(Boolean read) {
			this.read = read;
		}
		public Long getChatId() {
			return chatId;
		}
		public void setChatId(Long chatId) {
			this.chatId = chatId;
		}


}
