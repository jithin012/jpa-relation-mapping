package com.example.jpa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.Repository.CommentRepository;
import com.example.jpa.Repository.PostRepository;
import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Comment;

@RestController
public class CommentController {

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private PostRepository postRepo;

	@GetMapping("/posts/{postId}/comments")
	public Page<Comment> getAlllCommentByPostId(@PathVariable(value = "postId") Long postId, Pageable pageable) {
		return commentRepo.findByPostId(postId, pageable);
	}

	@PostMapping("/posts/{postId}/comments")
	public Comment createComment(@PathVariable(value = "postId") Long postId, @Valid @RequestBody Comment comment) {
		return postRepo.findById(postId).map(post -> {
			comment.setPost(post);
			return commentRepo.save(comment);
		}).orElseThrow(() -> new ResourceNotFoundException("Postod " + postId + " not found "));
	}

	@PutMapping("/posts/{postId}/comments/{commentId}")
	public Comment updateComment(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "commentId") Long commentId, @Valid @RequestBody Comment commentRequest) {
		if (!postRepo.existsById(postId)) {
			throw new ResourceNotFoundException("postId " + postId + " not found!!!");
		}
		return commentRepo.findById(commentId).map(comment -> {
			comment.setText(commentRequest.getText());
			return commentRepo.save(comment);
		}).orElseThrow(() -> new ResourceNotFoundException("Comment ID " + commentId + " not found "));
	}

	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "commentId") Long commentId) {
		return commentRepo.findByIdAndPostId(commentId, postId).map(comment -> {
			commentRepo.delete(comment);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException(
				"Comment not found with id " + commentId + " and postId " + postId));
	}
}
