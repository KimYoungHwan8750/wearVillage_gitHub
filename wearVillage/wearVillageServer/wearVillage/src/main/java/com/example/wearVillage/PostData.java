package com.example.wearVillage;

public class PostData {
    private String postId;
    private String postWriterId;
    private String postSubtitle;
    private String postText;
    private String postDate;
    private String postModifyDate;

    public String getPostId() {
        return this.postId;
    }

    public String getPostWriterId() {
        return this.postWriterId;
    }

     public String getPostSubtitle() {
        return this.postSubtitle;
     }

     public String getPostText() {
         return this.postText; 
     }
     
     public String getPostDate() {
         return this.postDate; 
     }
     
     public String getPostModifyDate() {
         return this.postModifyDate; 
     }

   public void setPostId(String postId) {
       this.postId = postId;  
   }
   
   public void setPostWriterId(String postWriterId) { 
       this.postWriterId = postWriterId;  
   }
   
   public void setPostSubtitle(String postSubtitle) { 
       this.postSubtitle = postSubtitle;  
   }
   
   public void setpostText(String postText) { 
       this.postText =  postText ;  
   }
   
   public void setpostDate(String postDate) { 
      this.postDate = postDate ;  
  }

  public void setpostModifyDate (String postModifyDate ) { 
      this.postModifyDate = postModifyDate ;  
  }  
}