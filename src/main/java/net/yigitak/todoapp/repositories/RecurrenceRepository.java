package net.yigitak.todoapp.repositories;


import net.yigitak.todoapp.models.Recurrence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface RecurrenceRepository
    extends MongoRepository< Recurrence, String > {

  @Query( """
          {
            "ownerId": ?0,
            "$or": [
              { "endDate": { "$exists": false } },
              { "endDate": { "$gte": ?1 } }
            ],
            "$or": [
              { "lastOccurrence": { "$exists": false } },
              { "lastOccurrence": { "$lt": ?1 } }
            ]
          }
          """ )
  List< Recurrence > findAllValid ( String ownerId , LocalDate date );

  List< Recurrence > findAllByOwnerId ( String ownerId );

  Optional< Recurrence > findByIdAndOwnerId ( String recurrenceId , String ownerId );

  void deleteByIdAndOwnerId ( String recurrenceId , String ownerId );

}
