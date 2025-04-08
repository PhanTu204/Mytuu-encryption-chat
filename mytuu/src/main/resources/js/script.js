function togglePassword(inputId, eyeIcon) {
    const passwordInput = document.getElementById(inputId);
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        eyeIcon.classList.remove("fa-eye");
        eyeIcon.classList.add("fa-eye-slash");
    } else {
        passwordInput.type = "password";
        eyeIcon.classList.remove("fa-eye-slash");
        eyeIcon.classList.add("fa-eye");
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const dateInput = document.getElementById("dob");

    dateInput.addEventListener("focus", function () {
        this.style.color = "#000"; // Khi nhập thì chữ rõ lại
    });

    dateInput.addEventListener("blur", function () {
        if (!this.value) {
            this.style.color = "#8B8B8B"; // Nếu không nhập gì, chữ lại mờ
        }
    });

    if (!dateInput.value) {
        dateInput.style.color = "#8B8B8B"; // Mặc định mờ
    }
});
document.addEventListener('DOMContentLoaded', function() {
    // Toggle between chat items
    const chatItems = document.querySelectorAll('.chat-item');
    
    chatItems.forEach(item => {
        item.addEventListener('click', function() {
            // Remove active class from all chat items
            chatItems.forEach(chat => {
                chat.classList.remove('active');
            });
            
            // Add active class to clicked item
            this.classList.add('active');
            
            // In a real app, we would load the appropriate chat content here
            // For now, we'll just simulate that the chat is already loaded
        });
    });
    
    // Toggle between filter options
    const filterOptions = document.querySelectorAll('.filter');
    
    filterOptions.forEach(filter => {
        filter.addEventListener('click', function() {
            // Remove active class from all filters
            filterOptions.forEach(f => {
                f.classList.remove('active');
            });
            
            // Add active class to clicked filter
            this.classList.add('active');
            
            // In a real app, we would filter the chat list here
        });
    });
    
    // Toggle between navigation icons
    const navIcons = document.querySelectorAll('.nav-icon');
    
    navIcons.forEach(icon => {
        icon.addEventListener('click', function() {
            // Remove active class from all nav icons
            navIcons.forEach(i => {
                i.classList.remove('active');
            });
            
            // Add active class to clicked icon
            this.classList.add('active');
            
            // In a real app, we would change the content area based on the selected nav item
        });
    });
    
    // Toggle profile options (expand/collapse)
    const profileOptions = document.querySelectorAll('.option');
    
    profileOptions.forEach(option => {
        option.addEventListener('click', function() {
            // Toggle expanded class for this option
            this.classList.toggle('expanded');
            
            // Toggle the chevron icon
            const chevron = this.querySelector('.fa-chevron-down, .fa-chevron-up');
            if (chevron) {
                chevron.classList.toggle('fa-chevron-down');
                chevron.classList.toggle('fa-chevron-up');
            }
            
            // Find the next element which should be the sub-options
            let nextElement = this.nextElementSibling;
            if (nextElement && nextElement.classList.contains('sub-options')) {
                // Toggle visibility of sub-options
                if (nextElement.style.display === 'none') {
                    nextElement.style.display = 'block';
                } else {
                    nextElement.style.display = 'none';
                }
            }
        });
    });
    
    // Handle message sending
    const messageInput = document.querySelector('.chat-input input');
    const sendButton = document.querySelector('.fa-paper-plane');
    
    function sendMessage() {
        const message = messageInput.value.trim();
        if (message !== '') {
            // Create message element
            const messagesContainer = document.querySelector('.messages-container');
            const currentTime = new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
            
            const messageElement = document.createElement('div');
            messageElement.className = 'message sent';
            messageElement.innerHTML = `
                <div class="message-content">
                    <div class="message-bubble">${message}</div>
                    <div class="message-time-sent">${currentTime}</div>
                </div>
            `;
            
            // Add message to container
            messagesContainer.appendChild(messageElement);
            
            // Clear input field
            messageInput.value = '';
            
            // Scroll to bottom
            messagesContainer.scrollTop = messagesContainer.scrollHeight;
            
            // In a real app, we would send the message to the server here
            
            // Simulate a reply after a short delay (for demo purposes)
            setTimeout(() => {
                const replyElement = document.createElement('div');
                replyElement.className = 'message received';
                replyElement.innerHTML = `
                    <div class="message-content">
                        <div class="message-bubble">I'm busy right now, talk to you later!</div>
                        <div class="message-time-received">${new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</div>
                    </div>
                `;
                
                messagesContainer.appendChild(replyElement);
                messagesContainer.scrollTop = messagesContainer.scrollHeight;
            }, 1000);
        }
    }
    
    // Send message on Enter key
    messageInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            sendMessage();
        }
    });
    
    // Send message on send button click
    sendButton.addEventListener('click', sendMessage);
    
    // Add functionality to emoji button (simplified)
    const emojiButton = document.querySelector('.fa-face-smile');
    
    emojiButton.addEventListener('click', function() {
        // In a real app, we would open an emoji picker here
        // For this demo, let's just insert a smiley emoji
        messageInput.value += ' 😊 ';
        messageInput.focus();
    });
    
    // Mock functionality for plus button
    const plusButton = document.querySelector('.input-button');
    
    plusButton.addEventListener('click', function() {
        // In a real app, we would show options for attachments
        alert('Attachment options would appear here!');
    });
    
    // Initialize: Make sure only the active option has sub-options visible
    const initialSubOptions = document.querySelectorAll('.sub-options');
    initialSubOptions.forEach(subOption => {
        if (!subOption.previousElementSibling.classList.contains('expanded')) {
            subOption.style.display = 'none';
        }
    });
});
document.addEventListener('DOMContentLoaded', function() {
    const filterOptions = document.querySelectorAll('.filter');
    const chatItems = document.querySelectorAll('.chat-item');

    // Hàm lọc tin nhắn
    function filterChats(filterType) {
        chatItems.forEach(chat => {
            if (filterType === 'all') {
                chat.style.display = 'flex';
            } else if (filterType === 'unread' && chat.classList.contains('unread')) {
                chat.style.display = 'flex';
            } else if (filterType === 'group' && chat.classList.contains('group-chat')) {
                chat.style.display = 'flex';
            } else {
                chat.style.display = 'none';
            }
        });
    }

    // Tự động kích hoạt "All" khi vào trang
    filterOptions.forEach(option => {
        if (option.textContent.trim() === 'All') {
            option.classList.add('active');
            filterChats('all');
        }
    });

    // Lắng nghe sự kiện click cho các filter
    filterOptions.forEach(filter => {
        filter.addEventListener('click', function() {
            filterOptions.forEach(f => f.classList.remove('active'));
            this.classList.add('active');

            const filterType = this.textContent.trim().toLowerCase();
            filterChats(filterType);
        });
    });
});

let currentEndpoint = ''; // Biến lưu trữ endpoint của người dùng hiện tại
let currentChatId = null; // Biến lưu trữ chat hiện tại

// Mở chat và cập nhật endpoint và thông tin chat
function openChat(chatId, userId) {
    currentChatId = chatId;
    currentEndpoint = `https://api.example.com/messages/${userId}`;

    // Cập nhật giao diện chat
    const activeChatItem = document.querySelector('.chat-item.active');
    if (activeChatItem) {
        activeChatItem.classList.remove('active');
    }
    const selectedChatItem = document.querySelector(`.chat-item:nth-child(${chatId})`);
    selectedChatItem.classList.add('active');

    // Cập nhật thông tin người dùng trong phần chat header
    updateChatHeader(userId);

    // Tải tin nhắn từ API
    loadMessages();
}

// Cập nhật thông tin người dùng trong phần chat header
function updateChatHeader(userId) {
    // Giả sử bạn có thông tin người dùng theo ID, bạn có thể gọi API hoặc lấy từ một đối tượng đã có
    fetch(`https://api.example.com/user/${userId}`)
        .then(response => response.json())
        .then(user => {
            // Cập nhật avatar và tên người dùng
            const avatar = document.querySelector('.chat-header .user-info img');
            const userName = document.querySelector('.chat-header .user-details h3');
            const userStatus = document.querySelector('.chat-header .user-details small');
            
            avatar.src = user.avatarUrl;
            userName.textContent = user.name;
            userStatus.textContent = user.status;
        });
}

// Tải tin nhắn từ API
function loadMessages() {
    fetch(currentEndpoint)
        .then(response => response.json())
        .then(data => {
            const messageContainer = document.getElementById('messages-container');
            messageContainer.innerHTML = ''; // Xóa các tin nhắn hiện tại

            data.messages.forEach(msg => {
                const messageDiv = document.createElement('div');
                messageDiv.classList.add('message', msg.isUser ? 'sent' : 'received');
                
                const messageContent = document.createElement('div');
                messageContent.classList.add('message-content');
                
                const messageBubble = document.createElement('div');
                messageBubble.classList.add('message-bubble');
                messageBubble.textContent = msg.text;
                
                messageContent.appendChild(messageBubble);
                messageDiv.appendChild(messageContent);
                messageContainer.appendChild(messageDiv);
            });

            // Cuộn xuống tin nhắn mới nhất
            messageContainer.scrollTop = messageContainer.scrollHeight;
        })
        .catch(error => console.error('Error loading messages:', error));
}

// Gửi tin nhắn
function sendMessage(event) {
    if (event.key === 'Enter') {
        const inputField = document.getElementById('chat-input');
        const message = inputField.value.trim();

        if (message) {
            // Thêm tin nhắn vào vùng tin nhắn
            const messageContainer = document.getElementById('messages-container');
            const newMessage = document.createElement('div');
            newMessage.classList.add('message', 'sent');
            
            const messageContent = document.createElement('div');
            messageContent.classList.add('message-content');
            
            const messageBubble = document.createElement('div');
            messageBubble.classList.add('message-bubble');
            messageBubble.textContent = message;
            
            messageContent.appendChild(messageBubble);
            messageDiv.appendChild(messageContent);
            messageContainer.appendChild(messageDiv);
            
            // Cuộn xuống tin nhắn mới
            messageContainer.scrollTop = messageContainer.scrollHeight;

            // Gửi tin nhắn tới API
            fetch(currentEndpoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ text: message }),
            })
            .then(response => response.json())
            .then(data => {
                // Thêm phản hồi từ API nếu cần
                console.log('Message sent:', data);
            })
            .catch(error => console.error('Error sending message:', error));

            // Xóa nội dung ô input
            inputField.value = '';
        }
    }
}

document.getElementById("btn-toggle-sidebar").addEventListener("click", function() {
    document.querySelector(".profile-panel").classList.toggle("absolute");
});

const profileIcon = document.getElementById('profile-icon');
const overlay = document.getElementById('profile-overlay');

profileIcon.addEventListener('click', () => {
    overlay.classList.toggle('active');
});

//edit->save changes
let selectedAvatarBase64 = null;

document.getElementById('btn-edit').addEventListener('click', function () {
    const fields = document.querySelectorAll('.inf-field, .info-field input[type="radio"]');
    const changePhotoBtn = document.querySelector('.btn-change-photo');
    const avatarImg = document.getElementById('profile-avatar');

    const isEditing = fields[0].disabled;

    fields.forEach(field => {
        field.disabled = !isEditing;
    });

    changePhotoBtn.style.display = isEditing ? 'inline-block' : 'none';

    if (!isEditing) {
        // Bấm "Save changes"
        if (selectedAvatarBase64) {
            avatarImg.src = selectedAvatarBase64;
            avatarImg.setAttribute('data-original-src', selectedAvatarBase64);
            selectedAvatarBase64 = null;
        }
        this.textContent = 'Edit';
    } else {
        // Bấm "Edit"
        avatarImg.src = avatarImg.getAttribute('data-original-src');
        this.textContent = 'Save changes';
    }
});

document.querySelector('.btn-change-photo').addEventListener('click', function () {
    document.getElementById('avatarInput').click();
});

document.getElementById('avatarInput').addEventListener('change', function (event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            // Lưu lại preview vào biến tạm
            selectedAvatarBase64 = e.target.result;
            document.getElementById('profile-avatar').src = selectedAvatarBase64;
        };
        reader.readAsDataURL(file);
    }
});

document.addEventListener('DOMContentLoaded', function() {
    // Friend items click event
    const friendItems = document.querySelectorAll('.friend-item');
    
    friendItems.forEach(item => {
        item.addEventListener('click', function() {
            // Remove active class from all items
            friendItems.forEach(i => i.classList.remove('active'));
            
            // Add active class to clicked item
            this.classList.add('active');
            
            // Get friend name
            const friendName = this.querySelector('.friend-name').textContent;
            
            // Update profile details (in a real app, this would fetch data from server)
            updateProfileDetails(friendName);
        });
    });
    
    // Make the first friend active by default
    if (friendItems.length > 0) {
        friendItems[0].classList.add('active');
    }
    
    // Search functionality
    const searchInput = document.querySelector('.search-box input');
    
    searchInput.addEventListener('input', function() {
        const searchTerm = this.value.toLowerCase();
        
        friendItems.forEach(item => {
            const name = item.querySelector('.friend-name').textContent.toLowerCase();
            
            if (name.includes(searchTerm)) {
                item.style.display = 'flex';
            } else {
                item.style.display = 'none';
            }
        });
    });
    
    // Side menu items click event
    const sideMenuItems = document.querySelectorAll('.side-menu-item');
    
    sideMenuItems.forEach(item => {
        item.addEventListener('click', function() {
            // Remove active class from all items
            sideMenuItems.forEach(i => i.classList.remove('active'));
            
            // Add active class to clicked item
            this.classList.add('active');
            
            // In a real app, this would load different content
            console.log('Menu item clicked:', this.querySelector('span').textContent);
        });
    });
    
    // Main menu items click event
    const menuItems = document.querySelectorAll('.menu-item');
    
    menuItems.forEach(item => {
        item.addEventListener('click', function() {
            // Remove active class from all items
            menuItems.forEach(i => i.classList.remove('active'));
            
            // Add active class to clicked item
            this.classList.add('active');
            
            // In a real app, this would load different pages
            console.log('Main menu item clicked');
        });
    });
});

// Function to update profile details based on friend name
function updateProfileDetails(name) {
    // In a real app, this would fetch data from a server
    // For this demo, we'll just update the name
    document.querySelector('.profile-name').textContent = name;
    
    // Simulated data for the demo
    const userData = {
        'Hoàng Hà My': {
            email: 'hoanghamy2k4@gmail.com',
            phone: '0912345678',
            dob: '14/10/2004',
            gender: 'Female'
        },
        'Nguyễn Dũng': {
            email: 'nguyendung@gmail.com',
            phone: '0987654321',
            dob: '25/05/1999',
            gender: 'Male'
        },
        'Nguyễn Trọng Khang': {
            email: 'trongkhang@gmail.com',
            phone: '0909123456',
            dob: '30/12/2000',
            gender: 'Male'
        },
        'Mai Hồng Vũ': {
            email: 'hongvu@gmail.com',
            phone: '0932145678',
            dob: '18/07/2001',
            gender: 'Male'
        },
        'Hứa Quang Hán': {
            email: 'quanghan@gmail.com',
            phone: '0945123789',
            dob: '05/03/1998',
            gender: 'Male'
        }
    };
    
    // If we have data for this user, update the profile
    if (userData[name]) {
        const user = userData[name];
        document.querySelector('.profile-email').textContent = user.email;
        document.querySelector('.info-item:nth-child(1) .info-value').textContent = user.phone;
        document.querySelector('.info-item:nth-child(2) .info-value').textContent = user.dob;
        document.querySelector('.info-item:nth-child(3) .info-value').textContent = user.gender;
    }
}

// script.js
document.querySelectorAll('.friend-item').forEach(item => {
    item.addEventListener('click', () => {
      document.querySelectorAll('.friend-item').forEach(el => el.classList.remove('active'));
      item.classList.add('active');
    });
  });

  async function registerUser() {
    const username = document.querySelector('input[placeholder="Username"]').value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirm-password").value;
    const privacyChecked = document.getElementById("privacy").checked;

    if (!privacyChecked) {
        alert("Bạn cần đồng ý với chính sách quyền riêng tư.");
        return;
    }

    const body = {
        username,
        password,
        confirmPassword
    };

    try {
        const response = await fetch("http://localhost:8080/api/users/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        });

        const result = await response.json();

        if (response.ok) {
            alert("Đăng ký thành công!");
            window.location.href = "login.html"; // Nếu có trang đăng nhập
        } else {
            alert("Lỗi: " + result);
        }
    } catch (error) {
        console.error("Lỗi khi gọi API:", error);
        alert("Đã có lỗi xảy ra.");
    }
}