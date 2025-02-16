package com.app.shared.domain.model;

/**
 * A generic record representing a message with an address and a body.
 *
 * @param <B> The type of the message body.
 * @param address The destination address of the message.
 * @param body The content of the message.
 */
public record Message<B>(String address, B body) {

}
