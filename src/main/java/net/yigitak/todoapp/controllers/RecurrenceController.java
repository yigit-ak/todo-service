package net.yigitak.todoapp.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.yigitak.todoapp.annotations.JwtSubject;
import net.yigitak.todoapp.dto.CreateRecurrenceDto;
import net.yigitak.todoapp.models.Recurrence;
import net.yigitak.todoapp.services.RecurrenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping( "api/v1/recurrences" )
@RequiredArgsConstructor
public class RecurrenceController {

  private final RecurrenceService recurrenceService;


  @GetMapping
  public ResponseEntity< List< Recurrence > > getAllRecurrences (
      @JwtSubject String userId
  ) {

    List< Recurrence > recurrences = recurrenceService.getAllRecurrencesByOwnerId( userId );
    return ResponseEntity.ok( recurrences );
  }


  @PostMapping
  public ResponseEntity< Recurrence > createRecurrence (
      @JwtSubject String userId ,
      @RequestBody @Valid CreateRecurrenceDto dto
  ) {

    Recurrence newRecurrence = recurrenceService.createRecurrence( dto , userId );
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path( "/{id}" )
        .buildAndExpand( newRecurrence.getId() )
        .toUri();
    return ResponseEntity.created(location).body(newRecurrence);
  }


  @GetMapping( "/{recurrence-id}" )
  public ResponseEntity< Recurrence > getRecurrenceById (
      @JwtSubject String userId ,
      @PathVariable( "recurrence-id" ) String recurrenceId
  ) {

    Recurrence recurrence = recurrenceService.getRecurrenceByIdAndOwnerId( recurrenceId , userId );
    return ResponseEntity.ok( recurrence );
  }


  @DeleteMapping( "/{recurrence-id}" )
  public ResponseEntity< Void > deleteRecurrenceById (
      @JwtSubject String userId ,
      @PathVariable( "recurrence-id" ) String recurrenceId
  ) {

    recurrenceService.deleteRecurrenceByIdAndOwnerId( recurrenceId , userId );
    return ResponseEntity.noContent().build();
  }

  //  @PatchMapping("/{recurrence-id}") // todo
  //  public ResponseEntity<Recurrence> updateRecurrenceById(
  //      @JwtSubject String userId,
  //      @PathVariable("recurrence-id") String recurrenceId,
  //      @RequestBody Map<String, Object> dto) {
  //
  //    recurrenceService.updateRecurrenceByIdAndOwnerId(recurrenceId, userId, dto);
  //    return ResponseEntity.noContent().build();
  //  }
}
