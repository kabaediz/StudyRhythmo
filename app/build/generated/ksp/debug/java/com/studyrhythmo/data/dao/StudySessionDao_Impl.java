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
import com.studyrhythmo.data.entity.StudySessionEntity;
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
public final class StudySessionDao_Impl implements StudySessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StudySessionEntity> __insertionAdapterOfStudySessionEntity;

  private final EntityDeletionOrUpdateAdapter<StudySessionEntity> __deletionAdapterOfStudySessionEntity;

  private final EntityDeletionOrUpdateAdapter<StudySessionEntity> __updateAdapterOfStudySessionEntity;

  public StudySessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudySessionEntity = new EntityInsertionAdapter<StudySessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `study_sessions` (`id`,`courseId`,`topic`,`plannedStart`,`plannedDurationMinutes`,`actualDurationMinutes`,`isCompleted`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudySessionEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getCourseId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getCourseId());
        }
        statement.bindString(3, entity.getTopic());
        statement.bindLong(4, entity.getPlannedStart());
        statement.bindLong(5, entity.getPlannedDurationMinutes());
        statement.bindLong(6, entity.getActualDurationMinutes());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__deletionAdapterOfStudySessionEntity = new EntityDeletionOrUpdateAdapter<StudySessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `study_sessions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudySessionEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfStudySessionEntity = new EntityDeletionOrUpdateAdapter<StudySessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `study_sessions` SET `id` = ?,`courseId` = ?,`topic` = ?,`plannedStart` = ?,`plannedDurationMinutes` = ?,`actualDurationMinutes` = ?,`isCompleted` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudySessionEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getCourseId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getCourseId());
        }
        statement.bindString(3, entity.getTopic());
        statement.bindLong(4, entity.getPlannedStart());
        statement.bindLong(5, entity.getPlannedDurationMinutes());
        statement.bindLong(6, entity.getActualDurationMinutes());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public Object insertSession(final StudySessionEntity session,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfStudySessionEntity.insertAndReturnId(session);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSession(final StudySessionEntity session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfStudySessionEntity.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSession(final StudySessionEntity session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfStudySessionEntity.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<StudySessionEntity>> getAllSessions() {
    final String _sql = "SELECT * FROM study_sessions ORDER BY plannedStart ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<List<StudySessionEntity>>() {
      @Override
      @NonNull
      public List<StudySessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
          final int _cursorIndexOfTopic = CursorUtil.getColumnIndexOrThrow(_cursor, "topic");
          final int _cursorIndexOfPlannedStart = CursorUtil.getColumnIndexOrThrow(_cursor, "plannedStart");
          final int _cursorIndexOfPlannedDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "plannedDurationMinutes");
          final int _cursorIndexOfActualDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "actualDurationMinutes");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<StudySessionEntity> _result = new ArrayList<StudySessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudySessionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpCourseId;
            if (_cursor.isNull(_cursorIndexOfCourseId)) {
              _tmpCourseId = null;
            } else {
              _tmpCourseId = _cursor.getLong(_cursorIndexOfCourseId);
            }
            final String _tmpTopic;
            _tmpTopic = _cursor.getString(_cursorIndexOfTopic);
            final long _tmpPlannedStart;
            _tmpPlannedStart = _cursor.getLong(_cursorIndexOfPlannedStart);
            final int _tmpPlannedDurationMinutes;
            _tmpPlannedDurationMinutes = _cursor.getInt(_cursorIndexOfPlannedDurationMinutes);
            final int _tmpActualDurationMinutes;
            _tmpActualDurationMinutes = _cursor.getInt(_cursorIndexOfActualDurationMinutes);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _item = new StudySessionEntity(_tmpId,_tmpCourseId,_tmpTopic,_tmpPlannedStart,_tmpPlannedDurationMinutes,_tmpActualDurationMinutes,_tmpIsCompleted);
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
  public Flow<List<StudySessionEntity>> getSessionsBetween(final long start, final long end) {
    final String _sql = "SELECT * FROM study_sessions WHERE plannedStart BETWEEN ? AND ? ORDER BY plannedStart ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    _argIndex = 2;
    _statement.bindLong(_argIndex, end);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<List<StudySessionEntity>>() {
      @Override
      @NonNull
      public List<StudySessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
          final int _cursorIndexOfTopic = CursorUtil.getColumnIndexOrThrow(_cursor, "topic");
          final int _cursorIndexOfPlannedStart = CursorUtil.getColumnIndexOrThrow(_cursor, "plannedStart");
          final int _cursorIndexOfPlannedDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "plannedDurationMinutes");
          final int _cursorIndexOfActualDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "actualDurationMinutes");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<StudySessionEntity> _result = new ArrayList<StudySessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudySessionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpCourseId;
            if (_cursor.isNull(_cursorIndexOfCourseId)) {
              _tmpCourseId = null;
            } else {
              _tmpCourseId = _cursor.getLong(_cursorIndexOfCourseId);
            }
            final String _tmpTopic;
            _tmpTopic = _cursor.getString(_cursorIndexOfTopic);
            final long _tmpPlannedStart;
            _tmpPlannedStart = _cursor.getLong(_cursorIndexOfPlannedStart);
            final int _tmpPlannedDurationMinutes;
            _tmpPlannedDurationMinutes = _cursor.getInt(_cursorIndexOfPlannedDurationMinutes);
            final int _tmpActualDurationMinutes;
            _tmpActualDurationMinutes = _cursor.getInt(_cursorIndexOfActualDurationMinutes);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _item = new StudySessionEntity(_tmpId,_tmpCourseId,_tmpTopic,_tmpPlannedStart,_tmpPlannedDurationMinutes,_tmpActualDurationMinutes,_tmpIsCompleted);
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
  public Flow<List<StudySessionEntity>> getSessionsByCourse(final long courseId) {
    final String _sql = "SELECT * FROM study_sessions WHERE courseId = ? ORDER BY plannedStart DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, courseId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<List<StudySessionEntity>>() {
      @Override
      @NonNull
      public List<StudySessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
          final int _cursorIndexOfTopic = CursorUtil.getColumnIndexOrThrow(_cursor, "topic");
          final int _cursorIndexOfPlannedStart = CursorUtil.getColumnIndexOrThrow(_cursor, "plannedStart");
          final int _cursorIndexOfPlannedDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "plannedDurationMinutes");
          final int _cursorIndexOfActualDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "actualDurationMinutes");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<StudySessionEntity> _result = new ArrayList<StudySessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudySessionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpCourseId;
            if (_cursor.isNull(_cursorIndexOfCourseId)) {
              _tmpCourseId = null;
            } else {
              _tmpCourseId = _cursor.getLong(_cursorIndexOfCourseId);
            }
            final String _tmpTopic;
            _tmpTopic = _cursor.getString(_cursorIndexOfTopic);
            final long _tmpPlannedStart;
            _tmpPlannedStart = _cursor.getLong(_cursorIndexOfPlannedStart);
            final int _tmpPlannedDurationMinutes;
            _tmpPlannedDurationMinutes = _cursor.getInt(_cursorIndexOfPlannedDurationMinutes);
            final int _tmpActualDurationMinutes;
            _tmpActualDurationMinutes = _cursor.getInt(_cursorIndexOfActualDurationMinutes);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _item = new StudySessionEntity(_tmpId,_tmpCourseId,_tmpTopic,_tmpPlannedStart,_tmpPlannedDurationMinutes,_tmpActualDurationMinutes,_tmpIsCompleted);
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
  public Object getSessionById(final long id,
      final Continuation<? super StudySessionEntity> $completion) {
    final String _sql = "SELECT * FROM study_sessions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<StudySessionEntity>() {
      @Override
      @Nullable
      public StudySessionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
          final int _cursorIndexOfTopic = CursorUtil.getColumnIndexOrThrow(_cursor, "topic");
          final int _cursorIndexOfPlannedStart = CursorUtil.getColumnIndexOrThrow(_cursor, "plannedStart");
          final int _cursorIndexOfPlannedDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "plannedDurationMinutes");
          final int _cursorIndexOfActualDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "actualDurationMinutes");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final StudySessionEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpCourseId;
            if (_cursor.isNull(_cursorIndexOfCourseId)) {
              _tmpCourseId = null;
            } else {
              _tmpCourseId = _cursor.getLong(_cursorIndexOfCourseId);
            }
            final String _tmpTopic;
            _tmpTopic = _cursor.getString(_cursorIndexOfTopic);
            final long _tmpPlannedStart;
            _tmpPlannedStart = _cursor.getLong(_cursorIndexOfPlannedStart);
            final int _tmpPlannedDurationMinutes;
            _tmpPlannedDurationMinutes = _cursor.getInt(_cursorIndexOfPlannedDurationMinutes);
            final int _tmpActualDurationMinutes;
            _tmpActualDurationMinutes = _cursor.getInt(_cursorIndexOfActualDurationMinutes);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _result = new StudySessionEntity(_tmpId,_tmpCourseId,_tmpTopic,_tmpPlannedStart,_tmpPlannedDurationMinutes,_tmpActualDurationMinutes,_tmpIsCompleted);
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
