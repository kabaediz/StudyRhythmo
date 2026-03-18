package com.studyrhythmo.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.studyrhythmo.data.entity.CourseEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CourseDao_Impl implements CourseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CourseEntity> __insertionAdapterOfCourseEntity;

  private final EntityDeletionOrUpdateAdapter<CourseEntity> __deletionAdapterOfCourseEntity;

  private final EntityDeletionOrUpdateAdapter<CourseEntity> __updateAdapterOfCourseEntity;

  public CourseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCourseEntity = new EntityInsertionAdapter<CourseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `courses` (`id`,`name`,`instructor`,`room`,`dayOfWeek`,`startTime`,`endTime`,`color`,`reminderMinutes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CourseEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getInstructor());
        statement.bindString(4, entity.getRoom());
        statement.bindLong(5, entity.getDayOfWeek());
        statement.bindString(6, entity.getStartTime());
        statement.bindString(7, entity.getEndTime());
        statement.bindLong(8, entity.getColor());
        statement.bindLong(9, entity.getReminderMinutes());
      }
    };
    this.__deletionAdapterOfCourseEntity = new EntityDeletionOrUpdateAdapter<CourseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `courses` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CourseEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCourseEntity = new EntityDeletionOrUpdateAdapter<CourseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `courses` SET `id` = ?,`name` = ?,`instructor` = ?,`room` = ?,`dayOfWeek` = ?,`startTime` = ?,`endTime` = ?,`color` = ?,`reminderMinutes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CourseEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getInstructor());
        statement.bindString(4, entity.getRoom());
        statement.bindLong(5, entity.getDayOfWeek());
        statement.bindString(6, entity.getStartTime());
        statement.bindString(7, entity.getEndTime());
        statement.bindLong(8, entity.getColor());
        statement.bindLong(9, entity.getReminderMinutes());
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public Object insertCourse(final CourseEntity course,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCourseEntity.insertAndReturnId(course);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCourse(final CourseEntity course,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCourseEntity.handle(course);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCourse(final CourseEntity course,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCourseEntity.handle(course);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CourseEntity>> getAllCourses() {
    final String _sql = "SELECT * FROM courses ORDER BY dayOfWeek, startTime";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"courses"}, new Callable<List<CourseEntity>>() {
      @Override
      @NonNull
      public List<CourseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfInstructor = CursorUtil.getColumnIndexOrThrow(_cursor, "instructor");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfReminderMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutes");
          final List<CourseEntity> _result = new ArrayList<CourseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CourseEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpInstructor;
            _tmpInstructor = _cursor.getString(_cursorIndexOfInstructor);
            final String _tmpRoom;
            _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            final int _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final int _tmpColor;
            _tmpColor = _cursor.getInt(_cursorIndexOfColor);
            final int _tmpReminderMinutes;
            _tmpReminderMinutes = _cursor.getInt(_cursorIndexOfReminderMinutes);
            _item = new CourseEntity(_tmpId,_tmpName,_tmpInstructor,_tmpRoom,_tmpDayOfWeek,_tmpStartTime,_tmpEndTime,_tmpColor,_tmpReminderMinutes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<CourseEntity>> getCoursesByDay(final int day) {
    final String _sql = "SELECT * FROM courses WHERE dayOfWeek = ? ORDER BY startTime";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, day);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"courses"}, new Callable<List<CourseEntity>>() {
      @Override
      @NonNull
      public List<CourseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfInstructor = CursorUtil.getColumnIndexOrThrow(_cursor, "instructor");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfReminderMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutes");
          final List<CourseEntity> _result = new ArrayList<CourseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CourseEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpInstructor;
            _tmpInstructor = _cursor.getString(_cursorIndexOfInstructor);
            final String _tmpRoom;
            _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            final int _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final int _tmpColor;
            _tmpColor = _cursor.getInt(_cursorIndexOfColor);
            final int _tmpReminderMinutes;
            _tmpReminderMinutes = _cursor.getInt(_cursorIndexOfReminderMinutes);
            _item = new CourseEntity(_tmpId,_tmpName,_tmpInstructor,_tmpRoom,_tmpDayOfWeek,_tmpStartTime,_tmpEndTime,_tmpColor,_tmpReminderMinutes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getCourseById(final long id, final Continuation<? super CourseEntity> $completion) {
    final String _sql = "SELECT * FROM courses WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CourseEntity>() {
      @Override
      @Nullable
      public CourseEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfInstructor = CursorUtil.getColumnIndexOrThrow(_cursor, "instructor");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfReminderMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutes");
          final CourseEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpInstructor;
            _tmpInstructor = _cursor.getString(_cursorIndexOfInstructor);
            final String _tmpRoom;
            _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            final int _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final int _tmpColor;
            _tmpColor = _cursor.getInt(_cursorIndexOfColor);
            final int _tmpReminderMinutes;
            _tmpReminderMinutes = _cursor.getInt(_cursorIndexOfReminderMinutes);
            _result = new CourseEntity(_tmpId,_tmpName,_tmpInstructor,_tmpRoom,_tmpDayOfWeek,_tmpStartTime,_tmpEndTime,_tmpColor,_tmpReminderMinutes);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
